package com.doudou.freechat.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 封装redis常用操作方法
 */
@Component
public class DDUtil {

    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.token}")
    private String token;
    @Value("${jwt.secret}")
    private String secret;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取redis中指定key的value
     */
    public String getRedisValue(String key) {
        boolean is = Boolean.TRUE.equals(redisTemplate.hasKey(key));
        if (!is) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 设置某个数据到redis，使用默认的过期时间
     */
    public void setRedisValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expiration, TimeUnit.SECONDS);
    }
    /**
     * 设置某个数据到redis，并指定过期时间
     */
    public void setRedisValue(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
    /**
     * 删除redis中指定key
     */
    public void delRedisValue(String key) {
        redisTemplate.delete(key);
    }
    /**
     * 删除redis中的一组keys
     */
    public void delRedisValue(Collection<String> keys) {
        redisTemplate.delete(keys);
    }
    /**
     * 解析cookies中的token,获取用户名
     * @return 解析失败null 解析成功返回用户名
     */
    public String getUserNameByToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userName = null;
        try {
            if (cookies != null) {
                for(Cookie c :cookies ){
                    if (c.getName().equals(token)) {
                        userName = Jwts.parser()
                                .setSigningKey(secret)
                                .parseClaimsJws(c.getValue().replace("Bearer", ""))
                                .getBody()
                                .getSubject();
                    }
                }
            }
        } catch (Exception e) {}
        return userName;
    }
    /**
     * 通过用户名创建token
     * @return token字符串
     */
    public String getToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
