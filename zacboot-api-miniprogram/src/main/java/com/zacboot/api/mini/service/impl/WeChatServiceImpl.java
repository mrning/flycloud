package com.zacboot.api.mini.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.zacboot.api.mini.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {

    @Lazy
    @Autowired
    private WxMaService wxMaService;

    @Override
    public String codeToSession(String code) {
        try {
            WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.jsCode2SessionInfo(code);
            log.info(wxMaJscode2SessionResult.toString());
            return wxMaJscode2SessionResult.getOpenid();
        } catch (WxErrorException e) {
            log.error("jsCode2SessionInfo error",e);
        }
        return null;
    }
}
