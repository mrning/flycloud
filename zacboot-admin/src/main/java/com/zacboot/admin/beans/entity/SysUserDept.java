package com.zacboot.admin.beans.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.common.base.basebeans.BaseEntity;
import lombok.Data;

@Data
@TableName("sys_user_dept")
public class SysUserDept extends BaseEntity {

	/**用户id*/
	private String userUuid;
	/**部门id*/
	private String deptUuid;

	public SysUserDept(String uuid, String userUuid, String deptUuid) {
		super();
		this.setUuid(uuid);
		this.userUuid = userUuid;
		this.deptUuid = deptUuid;
	}

	public SysUserDept(String userUuid, String deptUuid) {
		this.userUuid = userUuid;
		this.deptUuid = deptUuid;
	}
}
