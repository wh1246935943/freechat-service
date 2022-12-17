package com.doudou.freechat.service;

import com.doudou.freechat.dao.UserDao;

public interface UserService {

    UserDao getUserInfoById(Long userId);

    UserDao getUserInfoByName(String userName);
}
