package com.doudou.freechat.service.impl;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.dto.UserRegisterParamDto;
import com.doudou.freechat.mapper.UserMapper;
import com.doudou.freechat.service.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDao getUserInfoById(long userId) {
        return userMapper.getUserInfoById(userId);
    }

    @Override
    public UserDao getUserInfoByName(String userName) {
        return userMapper.getUserInfoByName(userName);
    }

    @Override
    public UserDao addUser(UserRegisterParamDto userRegisterParam) {
        String userName = userRegisterParam.getUserName();
        UserDao userDaoTemp = getUserInfoByName(userName);
        if (userDaoTemp == null) {
            UserDao userDao = new UserDao();
            BeanUtils.copyProperties(userRegisterParam, userDao);
            userDao.setCreateTime(getDateStr());
            userDao.setAccountStatus(1);
            userMapper.addUser(userDao);
            return userDao;
        }
        return null;
    }

    public String getDateStr() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateFormat.format(date);
        return dateStr;
    }
}
