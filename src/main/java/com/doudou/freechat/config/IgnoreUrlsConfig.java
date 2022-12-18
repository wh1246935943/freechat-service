package com.doudou.freechat.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置请求白名单
 */
@ConfigurationProperties(prefix = "ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
