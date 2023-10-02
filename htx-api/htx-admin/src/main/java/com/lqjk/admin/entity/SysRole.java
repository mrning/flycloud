package com.lqjk.admin.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lqjk.request.res.SysRoleResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @Author scott
 * @since 2018-12-19
 */
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole extends BaseEntity {

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

    public static <T> SysRole convertByRequest(T request) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(request, sysRole);
        return sysRole;
    }

    public SysRoleResponse convert(){
        SysRoleResponse res = new SysRoleResponse();
        BeanUtil.copyProperties(this,res);
        return res;
    }

}
