package com.doudou.freechat.filter;

import io.jsonwebtoken.Claims;
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
import java.util.ArrayList;
import java.util.List;

public class JwtFilter extends GenericFilterBean {

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
        List<String> ignoreUrls = new ArrayList<>();
        ignoreUrls.add("/auth/login");
        ignoreUrls.add("/auth/register");
        ignoreUrls.add("/auth/verCode");
        ignoreUrls.add("/common/pre");
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
                if (c.getName().equals("token")) {
                    jwtToken = c.getValue();
                    System.out.println(jwtToken);
                }
            }
        }

        try {
            Claims claims = Jwts.parser().setSigningKey("doudou@freechat")
                    .parseClaimsJws(jwtToken.replace("Bearer", "")).getBody();
            String userName = claims.getSubject();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, null, null);
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();

            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.setContentType("application/json;charset=utf-8");
            res.setStatus(403);
            PrintWriter out = res.getWriter();
            out.write("登录已过期或请求不存在");
            out.flush();
            out.close();
        }
    }
}
