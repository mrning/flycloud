package com.zacboot.api.mini.service.impl;

import com.zac.system.core.entity.mini.MiniUserEntity;
import com.zacboot.api.mini.beans.reponses.SaveUserInfoResponse;
import com.zacboot.api.mini.beans.requests.SaveUserInfoRequest;
import com.zacboot.api.mini.dao.MiniSysUserDao;
import com.zacboot.api.mini.mapper.MiniUserMapper;
import com.zacboot.api.mini.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<MiniUserMapper, MiniUserEntity> implements UserService {

    private final MiniSysUserDao userDao;

    @Override
    public SaveUserInfoResponse saveUserInfo(SaveUserInfoRequest request) {
        log.info(request.toString());
        SaveUserInfoResponse saveUser = new SaveUserInfoResponse();
        if (StringUtils.isNotBlank(request.getOpenId())) {
            MiniUserEntity userEntity = userDao.queryByOpenId(request.getOpenId());
            if (null == userEntity) {
                userEntity = request.toEntity();
                userDao.add(userEntity);
            }
            saveUser.setUserUuid(userEntity.getUuid());
            log.info(userEntity.toString());
        }
        return saveUser;
    }

}
