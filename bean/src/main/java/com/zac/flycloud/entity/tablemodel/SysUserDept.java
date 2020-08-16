package com.zac.flycloud.entity.tablemodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zac.flycloud.annotation.AutoColumn;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_user_dept")
public class SysUserDept implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键id*/
	@TableId(type = IdType.AUTO)
	@AutoColumn(isAutoIncrement = true)
	private Long id;

	@AutoColumn(isNull = false)
	private String uuid;

	/**用户id*/
	private String userUuid;
	/**部门id*/
	private String deptUuid;

	public SysUserDept(String uuid, String userUuid, String deptUuid) {
		super();
		this.uuid = uuid;
		this.userUuid = userUuid;
		this.deptUuid = deptUuid;
	}

	public SysUserDept(String userUuid, String deptUuid) {
		this.userUuid = userUuid;
		this.deptUuid = deptUuid;
	}
}
