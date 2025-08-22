package com.doudou.freechat.service.impl;

import com.doudou.freechat.service.VerCodeCacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class VerCodeCacheServiceImpl implements VerCodeCacheService {

    @Override
    @Cacheable(cacheNames = "verCodeCache")
    public String get(String key) {
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "verCodeCache", key = "#key")
    public void delete(String key) {}

    @Override
    @CachePut(cacheNames = "verCodeCache", key = "#key")
    public String save(String key, String value) {
        return value;
    }
}
