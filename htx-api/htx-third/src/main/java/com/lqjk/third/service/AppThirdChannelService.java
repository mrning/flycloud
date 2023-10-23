package com.lqjk.third.service;


import com.lqjk.third.beans.AppThirdChannel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libatou
 * @since 2023-01-30
 */
public interface AppThirdChannelService {

    AppThirdChannel getByChannel(String channel);

    AppThirdChannel selectByChannel(String channel);
}
