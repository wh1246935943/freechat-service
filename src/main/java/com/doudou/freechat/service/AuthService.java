package com.doudou.freechat.service;

import com.doudou.freechat.dto.UserRegisterParamDto;
import com.doudou.freechat.vo.LoginVo;
import com.doudou.freechat.vo.UserVo;

public interface AuthService {

    LoginVo login(String userName, String password);

    UserVo register(UserRegisterParamDto userRegisterParam);
}
