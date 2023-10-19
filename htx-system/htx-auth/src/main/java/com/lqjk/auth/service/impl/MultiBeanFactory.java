package com.lqjk.auth.service.impl;

import com.lqjk.auth.constant.ClientContent;
import com.lqjk.auth.service.ClientCommonService;
import com.lqjk.base.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiBeanFactory {

    public static ClientCommonService getService(String clientId) {
        return clientId.equals(ClientContent.CLIENT_APP.getClientId()) ?
                SpringContextUtils.getBean(ClientContent.CLIENT_APP.getClientService(), AppServiceImpl.class) :
                SpringContextUtils.getBean(ClientContent.CLIENT_ADMIN.getClientService(), AdminServiceImpl.class);
    }
}
