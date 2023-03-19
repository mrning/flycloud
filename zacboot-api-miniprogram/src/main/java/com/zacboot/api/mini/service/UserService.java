package com.zacboot.api.mini.service;

import com.zac.system.core.entity.mini.MiniUserEntity;
import com.zacboot.api.mini.beans.reponses.SaveUserInfoResponse;
import com.zacboot.api.mini.beans.requests.SaveUserInfoRequest;

public interface UserService extends BaseService<MiniUserEntity> {

    SaveUserInfoResponse saveUserInfo(SaveUserInfoRequest request);
}
