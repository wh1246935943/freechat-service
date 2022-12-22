package com.doudou.freechat.config;

import com.doudou.freechat.filter.JwtFilter;
import com.doudou.freechat.util.DDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.token}")
    private String token;
    @Value("${jwt.secret}")
    private String secret;

    @Resource
    IgnoreUrlsConfig ignoreUrlsConfig;

    @Resource
    DDUtil ddUtil;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                /**
                 * 由于使用的是JWT，我们这里不需要csrf
                 * 这里做一个简单得解释：
                 * JWT 由三部分组成：头部、载荷和签名
                 * 由于 JWT 包含了签名，因此 JWT 可以用来验证它的完整性，这就意味着，如果有人想要修改 JWT 中的任何内容，将会导致签名无效，因此服务器就会拒绝该 JWT
                 * 因此，使用 JWT 可以避免跨站请求伪造 (CSRF) 攻击。由于 JWT 的完整性已被验证，因此服务器可以放心地执行请求。
                 * 但这不能作为唯一得安全措施，比如他没法防止用户直接通过接口来请求，
                 */
                .csrf()
                .disable()
                .sessionManagement() // 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                ).permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll() // 跨域请求会先进行一次options请求
                .anyRequest().authenticated();
        httpSecurity.addFilterBefore(
                new JwtFilter(ddUtil, ignoreUrlsConfig.getUrls()),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
