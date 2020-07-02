package com.zac.flycloud.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUserRoleDto implements Serializable{
	private static final long serialVersionUID = 1L;

	/**部门id*/
	private String roleId;
	/**对应的用户id集合*/
	private List<String> userIdList;

	public SysUserRoleDto() {
		super();
	}

	public SysUserRoleDto(String roleId, List<String> userIdList) {
		super();
		this.roleId = roleId;
		this.userIdList = userIdList;
	}

}
