package com.zacboot.core.entity.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.core.entity.BaseEntity;
import lombok.Data;

/**
 * 系统字典
 */
@Data
@TableName("sys_dict")
public class SysDict extends BaseEntity {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    @TableField("sort_no")
    private Integer sortNo;

    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 父级名称
     */
    private String parentName;

}
