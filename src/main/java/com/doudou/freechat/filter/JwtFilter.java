package com.doudou.freechat.filter;

import com.doudou.freechat.util.DDUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class JwtFilter extends GenericFilterBean {

    private final List<String> ignoreUrls;
    private final DDUtil ddUtil;

    public JwtFilter(DDUtil ddUtil, List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
        this.ddUtil = ddUtil;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        // 过滤不需要鉴权的请求
        if (ignoreUrls.contains(req.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 获取token中的用户名信息
        String userName = ddUtil.getUserNameByToken(req);
        // 用户名存在则请求合法
        if (userName != null) {
            // 如果用户不存在于redis缓存中说明已经主动退出了
            if (null == ddUtil.getRedisValue(userName)) {
                checkFail(servletResponse);
                return;
            }
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, null, null);
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 解析失败
        checkFail(servletResponse);

    }

    /**
     * 当用户请求权限解析失败后统一返回错误信息
     * @param servletResponse 返回头对象
     */
    private void checkFail(ServletResponse servletResponse) throws IOException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setContentType("application/json;charset=utf-8");
        res.setStatus(403);
        PrintWriter out = res.getWriter();
        out.println("{\"message\": \"未登录或登录状态已过期\", \"code\": 401}");
        out.flush();
        out.close();
    }
}
