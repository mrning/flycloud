package com.zacboot.admin.work.beans.response;

import com.zacboot.system.core.entity.assess.AppUserAssess;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserAssessInfo {

    private String userUuid;

    private String userName;

    /**
     * 岗位职责
     */
    private String workListResponsibility;
    /**
     * 岗位流程
     */
    private String workListFlow;
    /**
     * 岗位清单
     */
    private String workListInventory;
    /**
     * 岗位档案
     */
    private String workListFiles;

    public UserAssessInfo converByDo(AppUserAssess appUserAssess){
        BeanUtils.copyProperties(appUserAssess,this);
        return this;
    }
}
