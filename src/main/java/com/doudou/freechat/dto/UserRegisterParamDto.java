package com.doudou.freechat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserRegisterParamDto {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @Email
    private String email;
    private String phoneNumber;
    private String nickName;
    private String avatar;
    private String personalitySign;
}
