package com.zacboot.api.mini.service;

import com.zacboot.api.mini.beans.reponses.SaveUserInfoResponse;
import com.zacboot.api.mini.beans.requests.SaveUserInfoRequest;
import com.zacboot.system.core.entity.mini.MiniUser;

public interface UserService extends BaseService<MiniUser> {

    SaveUserInfoResponse saveUserInfo(SaveUserInfoRequest request);
}
