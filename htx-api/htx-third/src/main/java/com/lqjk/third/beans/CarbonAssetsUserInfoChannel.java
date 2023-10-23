package com.lqjk.third.beans;

import lombok.Data;

/**
 * @ClassName CarbonAssetsUserInfoChannel
 * @Description TODO
 * @Author YJD
 * @Date 2023/3/15 15:05
 * @Version 1.0
 */
@Data
public class CarbonAssetsUserInfoChannel extends CarbonAssetsUserInfo{
    /**
     * 三方渠道编码
     */
    private String thirdChannel;

    /**
     * 用户碳资产总量主表
     */
    private Long assetsId;
}
