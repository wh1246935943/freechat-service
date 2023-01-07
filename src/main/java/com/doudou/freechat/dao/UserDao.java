package com.doudou.freechat.dao;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
public class UserDao {

  private long id;
  private String userName;
  private String nickName;
  private String password;
  private String personalitySign;
  private String phoneNumber;
  private int onlineState;
  private String region;
  private String avatar;
  private String email;
  private String historySessionList;
  private String outTradeNo;
  private String createTime;
  private int accountStatus;

}
