package com.doudou.freechat.service;

import com.doudou.freechat.dao.UserDao;

public interface AuthService {
    /**
     * 登录功能
     * @param userName 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String userName,String password);
}
