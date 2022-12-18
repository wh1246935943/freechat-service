package com.doudou.freechat.service;

import com.doudou.freechat.vo.LoginVo;

public interface AuthService {

    LoginVo login(String userName, String password);
}
