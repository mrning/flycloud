package com.zacboot.system.core.entity.assess;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.system.core.entity.BaseEntity;
import lombok.Data;

/**
 * 用户考核表
 */
@Data
@TableName("app_user_assess")
public class AppUserAssess extends BaseEntity {

    private String userUuid;

    private String userName;

    /**
     * 考核结果总分
     */
    private String totalScore;

    /**
     * 考核等级
     * A = 90分以上
     * B = 80-89分
     * C = 70-79分
     * D = 60-69分
     * E = 60分以下
     */
    private String assessLevel;

    /**
     * 岗位职责
     */
    private String workListResponsibility;
    /**
     * 岗位流程
     */
    private String workListFlow;
    /**
     * 岗位清单
     */
    private String workListInventory;
    /**
     * 岗位档案
     */
    private String workListFiles;
}
