package com.doudou.freechat.service.impl;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.mapper.UserMapper;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.vo.UserVo;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    public UserVo getUserInfoById(long userId) {
        UserVo userVo = new UserVo();
        UserDao userDao = userMapper.getUserInfoById(userId);
        if (userDao!= null) {
            BeanUtils.copyProperties(userDao, userVo);
        }
        return userVo;
    }

    public UserVo getUserInfoByName(String userName) {
        UserVo userVo = new UserVo();
        UserDao userDao = userMapper.getUserInfoByName(userName);
        if (userDao!= null) {
            BeanUtils.copyProperties(userDao, userVo);
        }
        return userVo;
    }

    public long addUser(UserDao userDao) {
        return userMapper.addUser(userDao);
    }
}
