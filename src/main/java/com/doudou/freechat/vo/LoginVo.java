package com.doudou.freechat.vo;

public class LoginVo {
    private Long id;

    private String token;

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
