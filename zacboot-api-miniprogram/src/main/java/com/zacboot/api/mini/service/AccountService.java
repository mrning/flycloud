package com.zacboot.api.mini.service;

import com.zacboot.api.mini.beans.reponses.PhoneLoginResponse;
import com.zacboot.api.mini.beans.requests.PhoneLoginRequest;

public interface AccountService {


    PhoneLoginResponse phoneLogin(PhoneLoginRequest request);
}
