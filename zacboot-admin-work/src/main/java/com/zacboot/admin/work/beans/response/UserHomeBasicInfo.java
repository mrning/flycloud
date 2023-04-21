package com.zacboot.admin.work.beans.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zacboot.system.core.entity.admin.SysUser;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class UserHomeBasicInfo {

    /**
     * 用户uuid
     */
    private String userUuid;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 考核分数
     */
    private String assesScore;

    /**
     * 部门
     */
    private String deptName;
    /**
     * 部门
     */
    private String deptUuid;

    /**
     * 岗位
     */
    private String roleName;
    /**
     * 岗位
     */
    private String roleUuid;

    /**
     * 入职时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime entryDate;

    /**
     * 上级领导
     */
    private String parentUserName;

    /**
     * 年假剩余
     */
    private Integer annualLeaveCount;

    /**
     * 三报是否提交
     */
    private Boolean threeReportRes;

    /**
     * 考核申诉是否有结果
     */
    private Boolean assessAppealRes;

    public static UserHomeBasicInfo converBySysUser(SysUser sysUser){
        UserHomeBasicInfo userHomeBasicInfo = new UserHomeBasicInfo();
        userHomeBasicInfo.setUserName(sysUser.getRealName());
        userHomeBasicInfo.setUserUuid(sysUser.getUuid());
        userHomeBasicInfo.setEntryDate(sysUser.getEntryDate());
        userHomeBasicInfo.setParentUserName(sysUser.getParentUserName());
        return userHomeBasicInfo;
    }

}
