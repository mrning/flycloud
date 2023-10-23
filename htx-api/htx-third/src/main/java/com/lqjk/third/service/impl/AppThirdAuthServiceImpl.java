package com.lqjk.third.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.lqjk.base.basebeans.Result;
import com.lqjk.third.beans.AppThirdAuth;
import com.lqjk.third.beans.CancelAuthVo;
import com.lqjk.third.beans.CustomerLogin;
import com.lqjk.third.beans.CustomerTokenThird;
import com.lqjk.third.mapper.AppThirdAuthMapper;
import com.lqjk.third.service.AppThirdAuthService;
import com.lqjk.third.service.CustomerTokenThirdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 三方APP授权记录表 服务实现类
 * </p>
 *
 * @author libatou
 * @since 2021-11-19
 */
@Slf4j
@Service
public class AppThirdAuthServiceImpl implements AppThirdAuthService {

    @Autowired
    private AppThirdAuthMapper appThirdAuthMapper;


    @Autowired
    private CustomerTokenThirdService customerTokenThirdService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 保存授权记录信息
     *
     * @param customerInformation
     * @param channel
     * @param authType
     */
    @Override
    public void addAuth(CustomerLogin customerInformation, String channel, String authType, String appName, String authToken) {
        Long ownerId = customerInformation.getOwnerId();
        AppThirdAuth authQuery = new AppThirdAuth();
        authQuery.setOwnerId(ownerId)
                .setChannel(channel)
                .setAppChannel(customerInformation.getAppChannel())
                .setAuthType(authType);
        List<AppThirdAuth> authList = appThirdAuthMapper.selectByAppThirdAuth(authQuery);
        Long authId = null;
        if (CollUtil.isEmpty(authList)) {
            AppThirdAuth thirdAuth = new AppThirdAuth();
            thirdAuth.setOwnerId(ownerId)
                    .setAuthTime(new Date())
                    .setChannel(channel)
                    .setAuthType(authType)
                    .setAuthToken(authToken)
                    .setStatus("1")
                    .setAppName(appName)
                    .setCreateTime(new Date())
                    .setUpdateTime(new Date())
                    .setAppChannel(customerInformation.getAppChannel())
                    .setUserNo(customerInformation.getUserNo());
            appThirdAuthMapper.insert(thirdAuth);
            authId = thirdAuth.getId();
            log.debug("授权保存：{}", thirdAuth);
        } else {
            AppThirdAuth authSaved = authList.get(0);
            if ("1".equals(authSaved.getStatus())
                    && authToken.equals(authSaved.getAuthToken())) {
                log.info("已授权，不需要更新。[{},{},{}]", ownerId, channel, authType);
                return;
            }
            AppThirdAuth thirdAuth = new AppThirdAuth();
            authId = authSaved.getId();
            thirdAuth.setId(authSaved.getId());
            thirdAuth.setAuthToken(authToken);
            thirdAuth.setStatus("1");
            thirdAuth.setUpdateTime(DateUtil.date());
            appThirdAuthMapper.update(thirdAuth);
            log.debug("授权更新：{}", thirdAuth);
        }
    }

    /**
     * 取消三方授权，更新授权信息状态
     *
     * @param cancelAuthVo
     */
    @Override
    public Result<String> cancelAuth(CancelAuthVo cancelAuthVo, CustomerLogin customerLogin) {
        AppThirdAuth authQuery = new AppThirdAuth();
        authQuery.setChannel(cancelAuthVo.getChannel())
                .setAuthType(cancelAuthVo.getAuthType())
                .setAuthToken(cancelAuthVo.getAuthToken());

        List<AppThirdAuth> authList = appThirdAuthMapper.selectByAppThirdAuth(authQuery);
        if (CollUtil.isNotEmpty(authList)) {
            for (AppThirdAuth authSaved : authList) {
                AppThirdAuth thirdAuth = new AppThirdAuth();
                thirdAuth.setId(authSaved.getId());
                thirdAuth.setStatus("0");
                thirdAuth.setUpdateTime(DateUtil.date());
                appThirdAuthMapper.update(thirdAuth);
            }
            if (null != authList.get(0) && null != authList.get(0).getOwnerId()) {
                CustomerTokenThird customerTokenThird = customerTokenThirdService.getByOwnerId(authList.get(0).getOwnerId());
                if (null != customerLogin) {
                    Set<String> keys = stringRedisTemplate.keys(String.format("qtx:authExt::%s*", customerTokenThird.getToken()));
                    if (CollUtil.isNotEmpty(keys)) {
                        stringRedisTemplate.delete(keys);
                    }
                }
                customerTokenThirdService.deleteCustomerToken(cancelAuthVo.getChannel(),customerTokenThird.getToken());
            }
        }
        log.debug("解除授权:{}-{}-{}", cancelAuthVo.getChannel(), cancelAuthVo.getAuthType(), cancelAuthVo.getAuthToken());
        return Result.success("响应成功");
    }

    @Override
    public boolean checkAuth(AppThirdAuth authQuery) {
        List<AppThirdAuth> authList = appThirdAuthMapper.selectByAppThirdAuth(authQuery);
        return CollUtil.isNotEmpty(authList);
    }
}
