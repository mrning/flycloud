package com.zacboot.system.core.util;

import com.zacboot.common.base.constants.RedisKey;
import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.common.base.utils.SpringContextUtils;
import com.zacboot.system.core.entity.admin.SysUser;

public class SysUtil {

    public static SysUser getCurrentUser() {
        RedisUtil redisUtil = SpringContextUtils.getApplicationContext().getBean(RedisUtil.class);
        String token = SpringContextUtils.getHttpServletRequest().getHeader("token");
        return (SysUser) redisUtil.get(RedisKey.LOGIN_TOKEN + ":" + token);
    }
}
