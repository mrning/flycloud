package com.zacboot.admin.work.beans.response;

import com.zacboot.system.core.entity.assess.AppUserTimeAssess;
import lombok.Data;

import java.util.List;

@Data
public class UserTimeAssessInfo {

    /**
     * 考勤标准
     */
    private List<AppUserTimeAssess> userTimeAssessList;

    /**
     * 是否全勤
     */
    private Boolean fullAttendance;
}
