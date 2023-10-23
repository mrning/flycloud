package com.lqjk.third.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.lqjk.third.beans.AppThirdChannel;
import com.lqjk.third.mapper.AppThirdChannelMapper;
import com.lqjk.third.service.AppThirdChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author libatou
 * @since 2023-01-30
 */
@Service

public class AppThirdChannelServiceImpl implements AppThirdChannelService {

    @Autowired
    private AppThirdChannelMapper appThirdChannelMapper;

    /**
     * 根据渠道号查询有效的渠道信息
     *
     * @param channel
     * @return
     */
    @Override
    public AppThirdChannel getByChannel(String channel) {
        AppThirdChannel query = new AppThirdChannel();
        query.setChannel(channel)
                .setDisabled(false);
        List<AppThirdChannel> list = appThirdChannelMapper.selectByAppThirdChannel(query);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    @Cacheable(key = "#p0", cacheNames = "thirdChannel:channel", unless = "#result == null")
    public AppThirdChannel selectByChannel(String channel) {
        return appThirdChannelMapper.selectByChannel(channel);
    }
}
