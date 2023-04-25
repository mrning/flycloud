package com.zacboot.admin.beans.vos.request;

import cn.hutool.db.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDeptRequest extends Page {

    @ApiModelProperty("部门uuid")
    private String deptUuid;
}
