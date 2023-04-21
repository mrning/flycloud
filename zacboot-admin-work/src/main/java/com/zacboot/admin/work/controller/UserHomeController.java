package com.zacboot.admin.work.controller;

import com.zacboot.admin.work.beans.response.UserHomeBasicInfo;
import com.zacboot.admin.work.service.UserHomeBasicService;
import com.zacboot.common.base.basebeans.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人主页
 */
@RestController
@RequestMapping("/userHome")
public class UserHomeController {

    @Autowired
    private UserHomeBasicService userHomeBasicService;

    @GetMapping("/basicInfo")
    public Result<UserHomeBasicInfo> getBasicInfo(){
        return Result.success(userHomeBasicService.getBasicInfo());
    }
}
