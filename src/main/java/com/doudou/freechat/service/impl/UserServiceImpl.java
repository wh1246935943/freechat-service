package com.doudou.freechat.service.impl;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.mapper.UserMapper;
import com.doudou.freechat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    public UserDao getUserInfoById(Long userId) {
        return userMapper.getUserInfoById(userId);
    }

    public UserDao getUserInfoByName(String userName) {
        return userMapper.getUserInfoByName(userName);
    }
}
