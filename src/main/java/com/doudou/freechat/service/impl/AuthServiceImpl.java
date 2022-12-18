package com.doudou.freechat.service.impl;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.service.AuthService;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.vo.LoginVo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secret}")
    private String secret;


    @Autowired
    UserService userService;

    @Override
    public LoginVo login(String userName, String password) {
        LoginVo loginVo = new LoginVo();

        UserDao userDao = userService.getUserInfoByName(userName);

        if (userDao != null && password.equals(userDao.getPassword())) {
            String jwt = Jwts.builder()
                    .setSubject(userDao.getUserName())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                    .signWith(SignatureAlgorithm.HS512, this.secret)
                    .compact();
            loginVo.setToken(jwt);
            loginVo.setId(userDao.getId());
        }

        return loginVo;
    }
}
