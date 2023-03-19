package com.zac.system.core.entity.mini;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.common.base.basebeans.BaseEntity;
import lombok.Data;

@Data
@TableName("mini_sys_user")
public class MiniUserEntity extends BaseEntity {

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
