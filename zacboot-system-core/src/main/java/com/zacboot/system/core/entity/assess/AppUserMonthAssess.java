package com.zacboot.system.core.entity.assess;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zacboot.system.core.entity.BaseEntity;
import lombok.Data;

/**
 * 月度工作内容考核
 */
@Data
@TableName("app_user_month_assess")
public class AppUserMonthAssess extends BaseEntity {

    private String userUuid;
    /**
     * 考核项目
     */
    private String checkItem;
    /**
     * 指标要求
     */
    private String targetRequire;
    /**
     * 标准
     */
    private String targetLevel;
    /**
     * 计算公式
     */
    private String calFormula;
    /**
     * 节点
     */
    private String node;
    /**
     * 权重
     */
    private String weightPer;
    /**
     * 得分
     */
    private String score;
}
