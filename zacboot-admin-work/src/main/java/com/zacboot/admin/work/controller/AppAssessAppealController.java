package com.zacboot.admin.work.controller;

import cn.hutool.db.Page;
import com.zacboot.admin.work.service.AppAssessAppealService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.core.entity.admin.AppAssessAppeal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile 考核申诉 
 * @date 2023年4月20日星期四
 * @author zac
 */
@Api(tags = "考核申诉")
@RestController
@RequestMapping("/appAssessAppeal")
@Slf4j
public class AppAssessAppealController {
    @Autowired
    private AppAssessAppealService appAssessAppealService;

    /**
     * AutoCreateFile add
     * @date 2023年4月20日星期四
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody AppAssessAppeal appAssessAppeal) {
        return Result.success(appAssessAppealService.add(appAssessAppeal));
    }

    /**
     * AutoCreateFile del
     * @date 2023年4月20日星期四
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody AppAssessAppeal appAssessAppeal) {
        return Result.success(appAssessAppealService.del(appAssessAppeal));
    }

    /**
     * AutoCreateFile update
     * @date 2023年4月20日星期四
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody AppAssessAppeal appAssessAppeal) {
        return Result.success(appAssessAppealService.update(appAssessAppeal));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年4月20日星期四
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<AppAssessAppeal>> queryPage(@RequestBody AppAssessAppeal appAssessAppeal, Page page) {
        return Result.success(appAssessAppealService.queryPage(appAssessAppeal,page));
    }
}