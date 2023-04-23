package com.zacboot.admin.work.beans.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppThreeReport;
import lombok.Data;

@Data
public class ThreeReportPageRequest {

    private String userName;

    private String deptUuid;

    private String roleUuid;

    private String reportType;

    private Long page;

    private Long limit;

    public AppThreeReport converToDo(){
        AppThreeReport appThreeReport = new AppThreeReport();
        appThreeReport.setUserName(this.getUserName());
        appThreeReport.setDeptUuid(this.getDeptUuid());
        appThreeReport.setRoleUuid(this.getRoleUuid());
        appThreeReport.setReportType(this.getReportType());
        return appThreeReport;
    }
}
