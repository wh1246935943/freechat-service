package com.doudou.freechat.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        Cookie[] cookies = req.getCookies();
        String jwtToken = "";
        if (cookies != null) {
            for(Cookie c :cookies ){
                if (c.getName().equals("token")) {
                    jwtToken = c.getValue();
                    System.out.println(jwtToken);
                }
            }
        }

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey("doudou@freechat")
                    .parseClaimsJws(jwtToken.replace("Bearer", ""));
            Claims claims = jws.getBody();
            String username = claims.getSubject();
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
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
