package com.lqjk.base.bizentity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lqjk.base.annotation.AutoColumn;
import com.lqjk.base.basebeans.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 部门表
 * <p>
 *
 * @Author zac
 * @Since 2019-01-22
 */
@Data
@TableName("sys_dept")
public class SysDept extends BaseEntity {

    /**
     * 父机构ID
     */
    private String parentUuid;
    /**
     * 机构/部门名称
     */
    private String departName;
    /**
     * 描述
     */
    private String description;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 部门领导名称
     */
    private String leaderUuid;
    /**
     * 地址
     */
    private String deptAddress;

    @TableField(exist = false)
    @AutoColumn(isIgnore = true)
    private List<SysDept> children;

}
