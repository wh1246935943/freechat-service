package com.doudou.freechat.filter;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class JwtFilter extends GenericFilterBean {

    private String token;
    private String secret;
    private List<String> ignoreUrls;

    public JwtFilter(String token, String secret, List<String> ignoreUrls) {
        this.token = token;
        this.secret = secret;
        this.ignoreUrls = ignoreUrls;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        /**
         * 过滤不需要鉴权的请求
         */
        if (ignoreUrls.contains(req.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        /**
         * 从cookies中获取token
         */
        Cookie[] cookies = req.getCookies();
        String jwtToken = null;
        if (cookies != null) {
            for(Cookie c :cookies ){
                if (c.getName().equals(token)) {
                    jwtToken = c.getValue();
                }
            }
        }
        /**
         * 解析token
         * 如果解析失败了则直接返回错误信息给前端
         */
        String userName = null;
        try {
            userName = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwtToken.replace("Bearer", ""))
                    .getBody()
                    .getSubject();
        } catch (Exception e) {}
        /**
         * 解析成功进入下一步
         */
        if (userName != null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, null, null);
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        /**
         * 解析失败
         */
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setContentType("application/json;charset=utf-8");
        res.setStatus(403);
        PrintWriter out = res.getWriter();
        out.println("{\"message\": \"未登录或登录状态已过期\", \"code\": 401}");
        out.flush();
        out.close();
    }
}
