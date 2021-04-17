package com.zac.flycloud.tablemodel;

import com.zac.flycloud.basebean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    }


}
