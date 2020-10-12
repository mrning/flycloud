package com.zac.flycloud.entity.tablemodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zac.flycloud.annotation.AutoColumn;
import com.zac.flycloud.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

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
