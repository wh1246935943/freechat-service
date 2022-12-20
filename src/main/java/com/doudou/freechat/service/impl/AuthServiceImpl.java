package com.doudou.freechat.service.impl;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.dto.UserRegisterParamDto;
import com.doudou.freechat.service.AuthService;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.vo.LoginVo;
import com.doudou.freechat.vo.UserVo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secret}")
    private String secret;
    @Resource
    UserService userService;

    @Override
    public LoginVo login(String userName, String password) {
        LoginVo loginVo = new LoginVo();
        UserDao userDao = userService.getUserInfoByName(userName);
        if (userDao != null && password.equals(userDao.getPassword())) {
            String jwt = Jwts.builder()
                    .setSubject(userDao.getUserName())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(SignatureAlgorithm.HS512, this.secret)
                    .compact();
            loginVo.setToken(jwt);
            loginVo.setId(userDao.getId());
        }
        return loginVo;
    }

    @Override
    public UserVo register(UserRegisterParamDto userRegisterParam) {
        String userName = userRegisterParam.getUserName();
        UserVo userVo = new UserVo();
        UserDao user = userService.getUserInfoByName(userName);
        if (user == null) {
            UserDao userDao = new UserDao();
            BeanUtils.copyProperties(userRegisterParam, userDao);
            userDao.setCreateTime(new Date().toString());
            userDao.setAccountStatus(1);
            long userId = userService.addUser(userDao);
            BeanUtils.copyProperties(userRegisterParam, userVo);
            userVo.setId(userDao.getId());
            return userVo;
        }
        return null;
    }
}
