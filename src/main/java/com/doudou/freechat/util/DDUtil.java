package com.doudou.freechat.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 封装redis常用操作方法
 */
@Component
public class DDUtil {

    private SecureRandom random = new SecureRandom();

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
    /**
     * 动态生成指定长度的随机数
     * @param length 生成随机数的长度
     * @return 生成的随机数
     */
    public String generateRandom(int length) {
        int randomInt = random.nextInt();
        int max = (int) Math.pow(10, length) - 1;
        randomInt = randomInt % max;
        return String.format("%0" + length + "d", randomInt);
    }
    /**
     * 校验手机号是否合法
     * @param phoneNumber 手机号
     * @return boolean
     */
    public boolean isPhoneNumber(String phoneNumber) {
        String regex = "^[1]([3][0-9]{1}|[5-9][0-9]{1})[0-9]{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
