package com.doudou.freechat.vo;

import lombok.Getter;
import lombok.Setter;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

@Setter
@Getter
public class UserVo {
    private long id;
    private String userName;
    private String nickName;
    private String personalitySign;
    private String phoneNumber;
    private int onlineState;
    private String region;
    private String avatar;
    private String email;
    private String outTradeNo;
    private String createTime;
    private int accountStatus;
}
