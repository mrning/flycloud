package com.zac.flycloud.entity.tablemodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zac.flycloud.annotation.AutoColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @AutoColumn(isNull = false)
    private String uuid;
    
    /**
     * 角色id
     */
    private String roleUuid;

    /**
     * 权限id
     */
    private String permissionUuid;
    
    /**
     * 数据权限
     */
    private String dataRuleUuids;

    public SysRolePermission() {
   	}
       
   	public SysRolePermission(String roleUuid, String permissionUuid) {
   		this.roleUuid = roleUuid;
   		this.permissionUuid = permissionUuid;
   	}

}
