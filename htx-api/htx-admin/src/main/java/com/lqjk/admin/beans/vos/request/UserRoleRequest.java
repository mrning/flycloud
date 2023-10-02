package com.lqjk.admin.beans.vos.request;

import cn.hutool.db.Page;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

@Data
public class UserRoleRequest extends Page {

    @SchemaProperty(name = "角色uuid")
    private String roleUuid;
}
