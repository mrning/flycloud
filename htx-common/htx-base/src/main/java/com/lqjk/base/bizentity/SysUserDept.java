package com.lqjk.base.bizentity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lqjk.base.basebeans.BaseEntity;
import lombok.Data;

@Data
@TableName("sys_user_dept")
public class SysUserDept extends BaseEntity {

	/**用户id*/
	private String userUuid;
	/**部门id*/
	private String deptUuid;
}
