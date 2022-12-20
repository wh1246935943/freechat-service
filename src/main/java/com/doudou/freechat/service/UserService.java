package com.doudou.freechat.service;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.vo.UserVo;

public interface UserService {

    UserVo getUserInfoById(long userId);

    UserVo getUserInfoByName(String userName);

    long addUser(UserDao userDao);
}
