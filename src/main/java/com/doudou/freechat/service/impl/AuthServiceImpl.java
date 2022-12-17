package com.doudou.freechat.service.impl;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.mapper.UserMapper;
import com.doudou.freechat.model.User;
import com.doudou.freechat.service.AuthService;
import com.doudou.freechat.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private static final  String JWT_KEY = "doudou@freechat";
    private static final  long EXP_TIME = 60 * 60 * 1000;

    @Autowired
    UserService userService;

    @Override
    public String login(String userName, String password) {
        String token = null;

        UserDao userDao = userService.getUserInfoByName(userName);

        if (userDao != null && password.equals(userDao.getPassword())) {
            String jwt = Jwts.builder()
                    .setSubject(userDao.getUserName())
                    .setExpiration(new Date(System.currentTimeMillis() + EXP_TIME))
                    .signWith(SignatureAlgorithm.HS512, JWT_KEY)
                    .compact();
            token = jwt;
        }

        return token;
    }
}
