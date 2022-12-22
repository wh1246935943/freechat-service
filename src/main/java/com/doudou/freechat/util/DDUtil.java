package com.doudou.freechat.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class DDUtil {

    @Value("${jwt.expiration}")
    private int expiration;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public String getRedisValue(String key) {
        boolean is = Boolean.TRUE.equals(redisTemplate.hasKey(key));
        if (!is) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    public void setRedisValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expiration, TimeUnit.SECONDS);
    }

    public void setRedisValue(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
}
