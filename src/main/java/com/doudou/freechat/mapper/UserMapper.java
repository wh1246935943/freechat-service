package com.doudou.freechat.mapper;

import com.doudou.freechat.dao.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    
    UserDao getUserInfoById(@Param("id") long id);

    UserDao getUserInfoByName(@Param("userName") String userName);

    List<UserDao> getUserByPhoneNumber(String phoneNumber);

    int addUser(UserDao record);

}
