package com.zacboot.api.mini.beans.dtos;

import lombok.Data;

@Data
public class MiniUserDto {

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickName;

}
