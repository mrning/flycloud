package com.zacboot.system.core.entity.assess;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.system.core.entity.BaseEntity;
import lombok.Data;

/**
 * 考勤标准
 */
@Data
@TableName("app_user_time_assess")
public class AppUserTimeAssess extends BaseEntity {

    private String userUuid;
    /**
     * 考核项
     */
    private String checkItem;
    /**
     * 指标要求
     */
    private String targetRequire;
    /**
     * 次数
     */
    private Integer count;
    /**
     * 次数单位
     */
    private String countUnit;
    /**
     * 详情数量
     */
    private Integer detailCount;
    /**
     * 详情单位
     */
    private String detailUnit;
}
