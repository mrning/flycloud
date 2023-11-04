package com.zac.admin.beans.dtos;

import com.zac.base.bizentity.SysPermission;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形列表用到
 *
 * @author: jeecg-boot
 */
@Data
public class TreeModel implements Serializable {

    private static final long serialVersionUID = 4013193970046502756L;

    private String key;

    private String title;

    private String slotTitle;

    private boolean isLeaf;

    private String icon;

    private Map<String, String> scopedSlots;

    private String parentUuid;

    private String label;

    private String value;

    private List<TreeModel> children;


    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public TreeModel(SysPermission permission) {
        this.key = permission.getUuid();
        this.icon = permission.getIcon();
        this.parentUuid = permission.getParentUuid();
        this.title = permission.getName();
        this.slotTitle = permission.getName();
        this.value = permission.getUuid();
        this.isLeaf = permission.isLeaf();
        this.label = permission.getName();
        if (!permission.isLeaf()) {
            this.children = new ArrayList<TreeModel>();
        }
    }

    public TreeModel(String key, String parentId, String slotTitle, boolean isLeaf) {
        this.key = key;
        this.parentUuid = parentId;
        this.slotTitle = slotTitle;
        Map<String, String> map = new HashMap(5);
        map.put("title", "hasDatarule");
        this.scopedSlots = map;
        this.isLeaf = isLeaf;
        this.value = key;
        if (!isLeaf) {
            this.children = new ArrayList<TreeModel>();
        }
    }

}
