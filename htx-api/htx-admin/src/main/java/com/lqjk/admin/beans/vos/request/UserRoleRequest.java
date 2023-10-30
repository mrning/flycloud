package com.lqjk.admin.beans.vos.request;

import com.lqjk.base.basebeans.PageRequest;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

@Data
public class UserRoleRequest extends PageRequest {

    @SchemaProperty(name = "角色uuid")
    private String roleUuid;
}
