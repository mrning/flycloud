package com.lqjk.admin.beans.dtos;

import com.lqjk.admin.entity.SysDept;
import com.lqjk.admin.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
  * 树形列表用到
 */
@Data
public class TreeDto implements Serializable {

	private static final long serialVersionUID = 4013193970046502756L;

	private String key;

	private String title;

	private String slotTitle;

	private boolean isLeaf;

	private String icon;

	private Integer ruleFlag;

	private Map<String,String> scopedSlots;

	private String parentUuid;

	private String label;

	private String value;

	private List<TreeDto> children;

	public TreeDto() {

	}

	/**
	 * 构建部门树
	 * @param sysDept
	 */
	public TreeDto(SysDept sysDept) {
        this.key = sysDept.getUuid();
        this.parentUuid = sysDept.getParentUuid();
        this.title = sysDept.getDepartName();
		this.slotTitle =  sysDept.getDepartName();
		this.value = sysDept.getUuid();
		this.label = sysDept.getDepartName();
	}

	public TreeDto(SysUser sysUser) {
        this.key = sysUser.getUuid();
        this.title = sysUser.getRealName();
		this.slotTitle =  sysUser.getRealName();
		this.value = sysUser.getUuid();
		this.label = sysUser.getRealName();
	}

	public SysDept convertToDept(){
		SysDept sysDept = new SysDept();
		sysDept.setUuid(this.getKey());
		sysDept.setParentUuid(this.getParentUuid());
		sysDept.setDepartName(this.getTitle());
		return sysDept;
	}

}
