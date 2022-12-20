package com.doudou.freechat.service.impl;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.mapper.UserMapper;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.vo.UserVo;
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
            userVo.setUserName(userDao.getUserName());
            userVo.setPersonalitySign(userDao.getPersonalitySign());
            userVo.setId(userDao.getId());
        }
        return userVo;
    }

    public UserDao getUserInfoByName(String userName) {
        return userMapper.getUserInfoByName(userName);
    }

    public long addUser(UserDao userDao) {
        return userMapper.addUser(userDao);
    }
}
