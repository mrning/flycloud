package com.zac.flycloud.dto;

import com.zac.flycloud.tablemodel.SysDept;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 树形列表用到
 */
@Data
public class TreeDto implements Serializable {

	private static final long serialVersionUID = 4013193970046502756L;

	private Long key;

	private String title;

	private String slotTitle;

	private boolean isLeaf;

	private String icon;

	private Integer ruleFlag;

	private Map<String,String> scopedSlots;

	private String parentId;

	private String label;

	private Long value;

	private List<TreeDto> children;

	public TreeDto() {

	}

	/**
	 * 构建部门树
	 * @param sysDeptDto
	 */
	public TreeDto(SysDept sysDeptDto) {
		this.key = sysDeptDto.getId();
		this.parentId = sysDeptDto.getParentId();
		this.title = sysDeptDto.getDepartName();
		this.slotTitle =  sysDeptDto.getDepartName();
		this.value = sysDeptDto.getId();
		this.label = sysDeptDto.getDepartName();
	}

	public TreeDto(Long key, String parentId, String slotTitle, Integer ruleFlag, boolean isLeaf) {
		this.key = key;
		this.parentId = parentId;
		this.ruleFlag=ruleFlag;
		this.slotTitle =  slotTitle;
		Map<String,String> map = new HashMap<String,String>();
		map.put("title", "hasDatarule");
		this.scopedSlots = map;
		this.isLeaf = isLeaf;
		this.value = key;
		if(!isLeaf) {
			this.children = new ArrayList<TreeDto>();
		}
	}
}
