package com.zacboot.admin.work.beans.response;

import com.zacboot.system.core.entity.assess.AppUserMonthAssess;
import lombok.Data;

import java.util.List;

@Data
public class UserMonthAssessInfo {

    private List<AppUserMonthAssess> userMonthAssessInfoList;

    /**
     * 奖分项
     */
    private List<String> rewardList;
    /**
     * 扣分项
     */
    private List<String> punishment;

    /**
     * 考核结果总分
     */
    private Integer totalScore;

    /**
     * 考核等级
     * A = 90分以上
     * B = 80-89分
     * C = 70-79分
     * D = 60-69分
     * E = 60分以下
     */
    private String assessLevel;
}
