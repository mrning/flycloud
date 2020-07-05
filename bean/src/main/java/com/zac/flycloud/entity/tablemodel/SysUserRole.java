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
 * 用户角色表
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @AutoColumn(isNull = false)
    private String uuid;
    
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
