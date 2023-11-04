package com.zac.base.bizentity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zac.base.basebeans.BaseEntity;
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

    public static <T> SysDict convertByRequest(T request) {
        SysDict sysDict = new SysDict();
        BeanUtil.copyProperties(request, sysDict);
        return sysDict;
    }

}
