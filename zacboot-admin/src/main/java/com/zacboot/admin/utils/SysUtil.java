package com.zacboot.admin.utils;

import com.zacboot.admin.beans.entity.SysUser;
import com.zacboot.common.base.constants.RedisKey;
import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.common.base.utils.SpringContextUtils;

public class SysUtil {

    public static SysUser getCurrentUser() {
        RedisUtil redisUtil = SpringContextUtils.getApplicationContext().getBean(RedisUtil.class);
        String token = SpringContextUtils.getHttpServletRequest().getHeader("token");
        return (SysUser) redisUtil.get(RedisKey.LOGIN_TOKEN + ":" + token);
    }
}
