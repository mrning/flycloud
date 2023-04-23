package com.zacboot.admin.work.beans.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppAssessAppeal;
import lombok.Data;

@Data
public class AppAssessAppealPageRequest {

    private String userUuid;

    private Long page;

    private Long limit;

    public AppAssessAppeal converToDo(){
        AppAssessAppeal appAssessAppeal = new AppAssessAppeal();
        appAssessAppeal.setUserUuid(this.getUserUuid());
        return appAssessAppeal;
    }
}
