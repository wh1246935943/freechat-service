package com.doudou.freechat.mapper;

import com.doudou.freechat.dao.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    
    UserDao getUserInfoById(@Param("id") Long id);

    UserDao getUserInfoByName(@Param("userName") String userName);

    Long addUser(UserDao record);

}
