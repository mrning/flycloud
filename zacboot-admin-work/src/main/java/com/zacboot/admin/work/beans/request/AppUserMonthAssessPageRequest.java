package com.zacboot.admin.work.beans.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zacboot.system.core.entity.assess.AppUserMonthAssess;
import lombok.Data;

@Data
public class AppUserMonthAssessPageRequest {

    private String userUuid;

    private Page<AppUserMonthAssess> page;

    public AppUserMonthAssess converToDo(){
        AppUserMonthAssess appUserMonthAssess = new AppUserMonthAssess();
        appUserMonthAssess.setUserUuid(this.getUserUuid());
        return appUserMonthAssess;
    }

}
