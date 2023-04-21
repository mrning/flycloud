package com.zacboot.admin.work.service;

import com.zacboot.system.core.response.weixin.QwUserVo;

import java.util.List;

public interface WeixinService {

    List<QwUserVo> getWxUsers();
}
