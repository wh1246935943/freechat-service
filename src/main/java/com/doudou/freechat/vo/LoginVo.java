package com.doudou.freechat.vo;

public class LoginVo {
    private long id;

    private String token;

    public void setId(long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
