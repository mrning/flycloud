package com.zac.auth.service.impl;

import com.zac.auth.constant.ClientContent;
import com.zac.auth.service.ClientCommonService;
import com.zac.base.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiBeanFactory {

    public static ClientCommonService getService(String clientId) {
        return clientId.equals(ClientContent.CLIENT_APP.getClientId()) ?
                SpringContextUtils.getBean(ClientContent.CLIENT_APP.getClientService(), AppServiceImpl.class) :
                SpringContextUtils.getBean(ClientContent.CLIENT_ADMIN.getClientService(), AdminServiceImpl.class);
    }
}
