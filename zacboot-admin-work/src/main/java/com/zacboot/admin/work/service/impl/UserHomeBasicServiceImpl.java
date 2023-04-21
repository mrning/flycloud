package com.zacboot.admin.work.service.impl;

import com.zacboot.admin.work.beans.response.UserHomeBasicInfo;
import com.zacboot.admin.work.service.UserHomeBasicService;
import com.zacboot.system.core.util.SysUtil;
import org.springframework.stereotype.Service;

@Service
public class UserHomeBasicServiceImpl implements UserHomeBasicService {

    @Override
    public UserHomeBasicInfo getBasicInfo() {
        UserHomeBasicInfo userHomeBasicInfo = UserHomeBasicInfo.converBySysUser(SysUtil.getCurrentUser());
        return userHomeBasicInfo;
    }
}
