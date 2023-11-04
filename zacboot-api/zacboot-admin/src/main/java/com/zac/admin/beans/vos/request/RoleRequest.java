package com.zac.admin.beans.vos.request;

import com.zac.base.basebeans.PageRequest;
import lombok.Data;

@Data
public class RoleRequest extends PageRequest {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;
}
