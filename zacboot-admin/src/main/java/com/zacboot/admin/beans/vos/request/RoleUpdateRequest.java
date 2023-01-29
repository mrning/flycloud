package com.zacboot.admin.beans.vos.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleUpdateRequest {

    /**
     * 角色uuid
     */
    private String uuid;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 权限uuid
     */
    private List<String> permissions;
}
