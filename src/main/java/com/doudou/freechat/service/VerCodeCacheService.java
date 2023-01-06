package com.doudou.freechat.service;

public interface VerCodeCacheService {
    String get(String key);

    String delete(String key);

    String save(String key, String value);

    String update(String key, String value);
}
