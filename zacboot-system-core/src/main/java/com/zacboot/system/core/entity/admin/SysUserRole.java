package com.zacboot.system.core.entity.admin;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.system.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity {

    /**
     * 用户id
     */
    private String userUuid;

    /**
     * 角色id
     */
    private String roleUuid;

    public SysUserRole() {
    }

    public SysUserRole(String userUuid, String roleUuid) {
        this.userUuid = userUuid;
        this.roleUuid = roleUuid;
        this.setUuid(UUID.randomUUID().toString(true));
        this.setCreateTime(LocalDateTime.now());
        this.setUpdateTime(LocalDateTime.now());
        this.setDeleted(Boolean.FALSE);
    }


}
