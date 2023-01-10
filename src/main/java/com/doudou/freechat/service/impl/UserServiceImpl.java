package com.doudou.freechat.service.impl;

import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.dto.UserRegisterParamDto;
import com.doudou.freechat.mapper.UserMapper;
import com.doudou.freechat.service.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    public List<UserDao> getUserByPhoneNumber(String phoneNumber) {
        return userMapper.getUserByPhoneNumber(phoneNumber);
    }

    public List<UserDao> getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    /**
     * 添加用户
     * @param userRegisterParam 用户注册表单信息
     * @return 返回用户注册成功的的用户信息，如果用户已经存在则返回null
     */
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
        return dateFormat.format(date);
    }

    /**
     * 删除用户
     * @param userName 被删除用户的if
     * @return 返回用户注册成功的的用户信息，如果用户已经存在则返回null
     */
    @Override
    public int deleteUser(String userName) {
        return userMapper.deleteUser(userName);
    }
}
