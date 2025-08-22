package com.doudou.freechat.service;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.dto.UserRegisterParamDto;

import java.util.List;

public interface UserService {

    UserDao getUserInfoById(long userId);

    UserDao getUserInfoByName(String userName);

    List<UserDao> getUserByPhoneNumber(String phoneNumber);

    List<UserDao> getUserByEmail(String email);

    UserDao addUser(UserRegisterParamDto userRegisterParam);
    // 登陆状态下才允许删除账号
    int deleteUser(String userName);
}
