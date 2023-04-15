package com.zac.system.core.entity.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.common.base.basebeans.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("sys_user_dept")
public class SysUserDept extends BaseEntity {

	/**用户id*/
	private String userUuid;
	/**部门id*/
	private String deptUuid;
}
