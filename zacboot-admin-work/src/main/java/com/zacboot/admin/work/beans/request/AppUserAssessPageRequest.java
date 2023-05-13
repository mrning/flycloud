package com.zacboot.admin.work.beans.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppUserAssess;
import lombok.Data;

@Data
public class AppUserAssessPageRequest {

    private String userUuid;

    private String userName;

    private Long page;

    private Long limit;

    public AppUserAssess converToDo(){
        AppUserAssess appUserAssess = new AppUserAssess();
        appUserAssess.setUserName(this.getUserName());
        appUserAssess.setUserUuid(this.getUserUuid());
        return appUserAssess;
    }
}
