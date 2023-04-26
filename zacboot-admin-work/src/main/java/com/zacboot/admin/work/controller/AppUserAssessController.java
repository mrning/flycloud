package com.zacboot.admin.work.controller;


import com.zacboot.admin.work.beans.request.AppUserAssessPageRequest;
import com.zacboot.admin.work.service.AppUserAssessService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.core.entity.assess.AppUserAssess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AutoCreateFile 用户考核表 
 * @date 2023年4月23日星期日
 * @author zac
 */
@Api(tags = "用户考核表")
@RestController
@RequestMapping("/appUserAssess")
@Slf4j
public class AppUserAssessController {
    @Autowired
    private AppUserAssessService appUserAssessService;

    /**
     * AutoCreateFile add
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody AppUserAssess appUserAssess) {
        return Result.success(appUserAssessService.add(appUserAssess));
    }

    /**
     * AutoCreateFile del
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody AppUserAssess appUserAssess) {
        return Result.success(appUserAssessService.del(appUserAssess));
    }

    /**
     * AutoCreateFile update
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody AppUserAssess appUserAssess) {
        return Result.success(appUserAssessService.update(appUserAssess));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<AppUserAssess>> queryPage(@RequestBody AppUserAssessPageRequest pageRequest) {
        return Result.success(appUserAssessService.queryPage(pageRequest));
    }
}