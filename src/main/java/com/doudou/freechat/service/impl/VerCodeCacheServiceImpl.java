package com.doudou.freechat.service.impl;

import com.doudou.freechat.service.VerCodeCacheService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class VerCodeCacheServiceImpl implements VerCodeCacheService {

    private final Map<String, String> entries = new HashMap<>();

    @Resource
    private CacheManager cacheManager;

    @Override
    @Cacheable(cacheNames = "verCodeCache")
    public String get(String key) {
        return key;
    }

    @Override
    @CacheEvict(cacheNames = "verCodeCache")
    public String delete(String key) {
        return entries.remove(key);
    }

    @Override
    @CachePut(cacheNames = "verCodeCache", key = "#key")
    public String save(String key, String value) {
        entries.put(key, value);
        return value;
    }

    @Override
    @CachePut(cacheNames = "verCodeCache", key = "#key")
    public String update(String key, String value) {
        return entries.put(key, value);
    }
}
