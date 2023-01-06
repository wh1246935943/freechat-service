package com.doudou.freechat.service;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.dto.UserRegisterParamDto;

import java.util.List;

public interface UserService {

    UserDao getUserInfoById(long userId);

    UserDao getUserInfoByName(String userName);

    List<UserDao> getUserByPhoneNumber(String phoneNumber);

    UserDao addUser(UserRegisterParamDto userRegisterParam);
}
