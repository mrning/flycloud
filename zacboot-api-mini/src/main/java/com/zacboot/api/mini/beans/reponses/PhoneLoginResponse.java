package com.zacboot.api.mini.beans.reponses;

import lombok.Data;

@Data
public class PhoneLoginResponse {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 用户昵称
     */
    private String nickName;
}
