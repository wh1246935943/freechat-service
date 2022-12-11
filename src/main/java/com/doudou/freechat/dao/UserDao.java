package com.doudou.freechat.dao;


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


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getPersonalitySign() {
    return personalitySign;
  }

  public void setPersonalitySign(String personalitySign) {
    this.personalitySign = personalitySign;
  }


  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }


  public long getOnlineState() {
    return onlineState;
  }

  public void setOnlineState(int onlineState) {
    this.onlineState = onlineState;
  }


  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getHistorySessionList() {
    return historySessionList;
  }

  public void setHistorySessionList(String historySessionList) {
    this.historySessionList = historySessionList;
  }


  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }


  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }


  public long getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(int accountStatus) {
    this.accountStatus = accountStatus;
  }

}
