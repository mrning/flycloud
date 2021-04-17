package com.zac.flycloud.tablemodel;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zac.flycloud.basebean.BaseEntity;
import lombok.Data;

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
    private String parentId;
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
     * 传真
     */
    private String fax;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注
     */
    private String memo;
    /**
     * 状态（1启用，0不启用）
     */
    private String status;

}
