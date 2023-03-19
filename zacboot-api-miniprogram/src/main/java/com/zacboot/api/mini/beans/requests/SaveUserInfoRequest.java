package com.zacboot.api.mini.beans.requests;

import cn.hutool.core.bean.BeanUtil;
import com.zac.system.core.entity.mini.MiniUserEntity;
import lombok.Data;

/**
 * 保存用户信息
 */
@Data
public class SaveUserInfoRequest {

    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 手机号
     */
    private String phoneNumber;

    public MiniUserEntity toEntity() {
        MiniUserEntity miniUser = new MiniUserEntity();
        BeanUtil.copyProperties(this,miniUser);
        return miniUser;
    }
}
