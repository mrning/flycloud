package com.zacboot.admin.work.controller;

import com.zacboot.admin.work.beans.response.UserAssessInfo;
import com.zacboot.admin.work.beans.response.UserHomeBasicInfo;
import com.zacboot.admin.work.beans.response.UserMonthAssessInfo;
import com.zacboot.admin.work.beans.response.UserTimeAssessInfo;
import com.zacboot.admin.work.service.AppUserAssessService;
import com.zacboot.admin.work.service.AppUserMonthAssessService;
import com.zacboot.admin.work.service.AppUserTimeAssessService;
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

    @Autowired
    private AppUserAssessService appUserAssessService;

    @Autowired
    private AppUserMonthAssessService appUserMonthAssessService;

    @Autowired
    private AppUserTimeAssessService appUserTimeAssessService;

    /**
     * 获取基础信息
     * @return
     */
    @GetMapping("/basicInfo")
    public Result<UserHomeBasicInfo> getBasicInfo(){
        return Result.success(userHomeBasicService.getBasicInfo());
    }

    /**
     * 获取考核信息，考核总分，岗位职责，流程等
     */
    @GetMapping("/userAssessInfo")
    public Result<UserAssessInfo> getUserAssessInfo(){
        return Result.success(appUserAssessService.getUserAssessInfo());
    }

    /**
     * 月度工作内容考核标准
     */
    @GetMapping("/userMonthAssessInfo")
    public Result<UserMonthAssessInfo> getUserMonthAssessInfo(){
        return Result.success(appUserMonthAssessService.getUserMonthAssessInfo());
    }

    /**
     * 考勤制度考核标准
     */
    @GetMapping("/userTimeAssessInfo")
    public Result<UserTimeAssessInfo> getUserTimeAssessInfo(){
        return Result.success(appUserTimeAssessService.getUserTimeAssessInfo());
    }
}
