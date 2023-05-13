package com.zacboot.admin.work.controller;

import com.zacboot.admin.work.beans.request.AppUserTimeAssessPageRequest;
import com.zacboot.admin.work.service.AppUserTimeAssessService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.core.entity.assess.AppUserTimeAssess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AutoCreateFile 考勤标准 
 * @date 2023年4月23日星期日
 * @author zac
 */
@Api(tags = "考勤标准")
@RestController
@RequestMapping("/api/admin/appUserTimeAssess")
@Slf4j
public class AppUserTimeAssessController {
    @Autowired
    private AppUserTimeAssessService appUserTimeAssessService;

    /**
     * AutoCreateFile add
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody AppUserTimeAssess appUserTimeAssess) {
        return Result.success(appUserTimeAssessService.add(appUserTimeAssess));
    }

    /**
     * AutoCreateFile del
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody AppUserTimeAssess appUserTimeAssess) {
        return Result.success(appUserTimeAssessService.del(appUserTimeAssess));
    }

    /**
     * AutoCreateFile update
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody AppUserTimeAssess appUserTimeAssess) {
        return Result.success(appUserTimeAssessService.update(appUserTimeAssess));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<AppUserTimeAssess>> queryPage(@RequestBody AppUserTimeAssessPageRequest pageRequest) {
        return Result.success(appUserTimeAssessService.queryPage(pageRequest));
    }
}