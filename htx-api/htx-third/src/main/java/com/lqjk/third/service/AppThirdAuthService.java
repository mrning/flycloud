package com.lqjk.third.service;

import com.lqjk.base.basebeans.Result;
import com.lqjk.third.beans.AppThirdAuth;
import com.lqjk.third.beans.CancelAuthVo;
import com.lqjk.third.beans.CustomerLogin;

/**
 * <p>
 * 三方APP授权记录表 服务类
 * </p>
 *
 * @author libatou
 * @since 2021-11-19
 */
public interface AppThirdAuthService {

    void addAuth(CustomerLogin customerInformation, String channel, String authType, String appName, String authToken);

    Result<String> cancelAuth(CancelAuthVo cancelAuthVo, CustomerLogin customerInformation);

    /**
     * 查询是否授权过
     *
     * @param authQuery
     * @return
     */
    boolean checkAuth(AppThirdAuth authQuery);
}
