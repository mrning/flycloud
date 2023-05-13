package com.zacboot.admin.work.service.impl;

import com.zacboot.admin.work.beans.response.UserHomeBasicInfo;
import com.zacboot.admin.work.feign.AdminFeign;
import com.zacboot.admin.work.service.UserHomeBasicService;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.core.request.admin.UserRequest;
import com.zacboot.system.core.response.admin.SysDeptResponse;
import com.zacboot.system.core.response.admin.SysRoleResponse;
import com.zacboot.system.core.response.admin.SysUserDeptAndRoleInfo;
import com.zacboot.system.core.util.SysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHomeBasicServiceImpl implements UserHomeBasicService {

    @Autowired
    private AdminFeign adminFeign;

    @Override
    public UserHomeBasicInfo getBasicInfo() {
        // 姓名，入职时间，上级领导
        UserHomeBasicInfo userHomeBasicInfo = UserHomeBasicInfo.converBySysUser(SysUtil.getCurrentUser());
        // 部门，角色
        UserRequest userRequest = new UserRequest();
        userRequest.setUserUuid(userHomeBasicInfo.getUserUuid());
        Result<SysUserDeptAndRoleInfo> deptAndRoleInfoResult = adminFeign.deptAndRoleInfo(userRequest);
        if (deptAndRoleInfoResult.isSuccess()){
            SysUserDeptAndRoleInfo sysUserDeptAndRoleInfo = deptAndRoleInfoResult.getResult();
            SysDeptResponse deptResponse = sysUserDeptAndRoleInfo.getDeptResponseList().stream().findAny().get();
            SysRoleResponse roleResponse = sysUserDeptAndRoleInfo.getRoleResponseList().stream().findAny().get();
            userHomeBasicInfo.setDeptUuid(deptResponse.getUuid());
            userHomeBasicInfo.setDeptName(deptResponse.getDepartName());
            userHomeBasicInfo.setRoleUuid(roleResponse.getUuid());
            userHomeBasicInfo.setRoleName(roleResponse.getRoleName());
        }
        // 年假剩余天数
        userHomeBasicInfo.setAnnualLeaveCount(10);
        return userHomeBasicInfo;
    }
}
