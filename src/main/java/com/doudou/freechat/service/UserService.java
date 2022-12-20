package com.doudou.freechat.service;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.vo.UserVo;

public interface UserService {

    UserVo getUserInfoById(Long userId);

    UserDao getUserInfoByName(String userName);

    long addUser(UserDao userDao);
}
