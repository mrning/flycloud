package com.zacboot.admin.work.controller;

import com.zacboot.system.core.response.weixin.QwUserVo;
import com.zacboot.admin.work.service.WeixinService;
import com.zacboot.common.base.basebeans.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx")
public class WeixinApiController {

    @Autowired
    private WeixinService weixinService;

    @PostMapping("/getWxUsers")
    public Result<List<QwUserVo>> getWxUsers(){
        return Result.success(weixinService.getWxUsers());
    }
}
