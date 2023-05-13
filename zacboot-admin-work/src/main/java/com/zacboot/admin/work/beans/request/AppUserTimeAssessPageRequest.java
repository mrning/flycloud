package com.zacboot.admin.work.beans.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppUserTimeAssess;
import lombok.Data;

@Data
public class AppUserTimeAssessPageRequest {

    private String userUuid;

    private Long page;

    private Long limit;

    public AppUserTimeAssess converToDo(){
        AppUserTimeAssess appUserTimeAssess = new AppUserTimeAssess();
        appUserTimeAssess.setUserUuid(this.getUserUuid());
        return appUserTimeAssess;
    }
}
