package com.zacboot.admin.work.beans.request;

import cn.hutool.db.Page;
import com.zacboot.system.core.entity.admin.AppThreeReport;
import lombok.Data;

@Data
public class ThreeReportPageRequest extends Page {

    private String userName;

    private String deptUuid;

    private String roleUuid;

    private String reportType;

    public AppThreeReport converToDo(){
        AppThreeReport appThreeReport = new AppThreeReport();
        appThreeReport.setUserName(this.getUserName());
        appThreeReport.setDeptUuid(this.getDeptUuid());
        appThreeReport.setRoleUuid(this.getRoleUuid());
        appThreeReport.setReportType(this.getReportType());
        return appThreeReport;
    }
}
