package com.doudou.freechat.service;

public interface VerCodeCacheService {
    String get(String key);

    void delete(String key);

    void save(String key, String value);

    void update(String key, String value);
}
