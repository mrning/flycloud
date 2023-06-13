package com.zacboot.api.mini.service.impl;

import com.zacboot.api.mini.beans.reponses.PhoneLoginResponse;
import com.zacboot.api.mini.beans.requests.PhoneLoginRequest;
import com.zacboot.api.mini.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * 账号服务
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public PhoneLoginResponse phoneLogin(PhoneLoginRequest request) {
        return null;
    }
}
