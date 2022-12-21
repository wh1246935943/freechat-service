package com.doudou.freechat.service;

import com.doudou.freechat.dao.UserDao;

public interface AuthService {
    UserDao login(String userName, String password);
}
