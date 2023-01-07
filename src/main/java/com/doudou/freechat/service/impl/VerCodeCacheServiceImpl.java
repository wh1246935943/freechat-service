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
    public void delete(String key) {
        entries.remove(key);
    }

    @Override
    @CachePut(cacheNames = "verCodeCache", key = "#key")
    public void save(String key, String value) {
        entries.put(key, value);
    }

    @Override
    @CachePut(cacheNames = "verCodeCache", key = "#key")
    public void update(String key, String value) {
        entries.put(key, value);
    }
}
