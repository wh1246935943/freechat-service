package com.doudou.freechat.mapper;

import com.doudou.freechat.dao.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // @Select("SELECT * FROM user WHERE id = #{id}")
    UserDao getUserInfoById(@Param("id") Long id);

    // @Select("SELECT * FROM user WHERE userName = #{userName}")
    UserDao getUserInfoByName(String userName);

}
