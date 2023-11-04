package com.zac.base.bizentity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zac.base.basebeans.BaseEntity;
import lombok.Data;

/**
 * 活动管理
 */
@Data
@TableName("sys_activity")
public class SysActivity extends BaseEntity {
    /**
     * 活动名称
     */
    private String actName;
    /**
     * 活动描述
     */
    private String actDesc;
    /**
     * 活动跳转链接
     */
    private String linkUrl;
    /**
     * 可参与用户标签
     */
    private String userTags;
    /**
     * 活动开始时间
     */
    private String startTime;
    /**
     * 活动结束时间
     */
    private String endTime;
    /**
     * 开屏广告资源url
     */
    private String openWindowUrl;
    /**
     * 首页弹窗广告资源
     */
    private String indexOpenUrl;

    public static <T> SysActivity convertByRequest(T request) {
        SysActivity sysActivity = new SysActivity();
        BeanUtil.copyProperties(request, sysActivity);
        return sysActivity;
    }
}
