package com.doudou.freechat.service;

public interface VerCodeCacheService {
    String get(String key);

    void delete(String key);

    String save(String key, String value);
}
