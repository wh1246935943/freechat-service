package com.doudou.freechat.dao;

public class ChatContentDao {

  private long id;
  private long sendId;
  private long receiverId;
  private String content;
  private int type;
  private int state;
  private String noCode;
  private String createDateUtc;
  private String title;
  private String description;
  private String label;
  private String thumbnail;
  private int readFlag;
  private String avatar;
  private int soundStatus;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getSendId() {
    return sendId;
  }

  public void setSendId(long sendId) {
    this.sendId = sendId;
  }


  public long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(long receiverId) {
    this.receiverId = receiverId;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }


  public long getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }


  public String getNoCode() {
    return noCode;
  }

  public void setNoCode(String noCode) {
    this.noCode = noCode;
  }


  public String getCreateDateUtc() {
    return createDateUtc;
  }

  public void setCreateDateUtc(String createDateUtc) {
    this.createDateUtc = createDateUtc;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }


  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }


  public long getReadFlag() {
    return readFlag;
  }

  public void setReadFlag(int readFlag) {
    this.readFlag = readFlag;
  }


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public long getSoundStatus() {
    return soundStatus;
  }

  public void setSoundStatus(int soundStatus) {
    this.soundStatus = soundStatus;
  }

}
