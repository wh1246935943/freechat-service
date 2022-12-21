package com.doudou.freechat.service;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.dto.UserRegisterParamDto;

public interface UserService {

    UserDao getUserInfoById(long userId);

    UserDao getUserInfoByName(String userName);

    UserDao addUser(UserRegisterParamDto userRegisterParam);
}
