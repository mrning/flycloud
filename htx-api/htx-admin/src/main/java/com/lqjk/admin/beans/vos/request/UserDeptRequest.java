package com.lqjk.admin.beans.vos.request;

import cn.hutool.db.Page;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

@Data
public class UserDeptRequest extends Page {

    @SchemaProperty(name = "部门uuid")
    private String deptUuid;
}
