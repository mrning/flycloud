package com.zacboot.admin.work.controller;

import com.zacboot.admin.work.beans.request.ThreeReportPageRequest;
import com.zacboot.admin.work.service.AppThreeReportService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.core.entity.admin.AppThreeReport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile 三报列表 
 * @date 2023年4月20日星期四
 * @author zac
 */
@Api(tags = "三报列表")
@RestController
@RequestMapping("/appThreeReport")
@Slf4j
public class AppThreeReportController {
    @Autowired
    private AppThreeReportService appThreeReportService;

    /**
     * AutoCreateFile add
     * @date 2023年4月20日星期四
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody AppThreeReport appThreeReport) {
        return Result.success(appThreeReportService.add(appThreeReport));
    }

    /**
     * AutoCreateFile del
     * @date 2023年4月20日星期四
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody AppThreeReport appThreeReport) {
        return Result.success(appThreeReportService.del(appThreeReport));
    }

    /**
     * AutoCreateFile update
     * @date 2023年4月20日星期四
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody AppThreeReport appThreeReport) {
        return Result.success(appThreeReportService.update(appThreeReport));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年4月20日星期四
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<AppThreeReport>> queryPage(@RequestBody ThreeReportPageRequest pageRequest) {
        return Result.success(appThreeReportService.queryPage(pageRequest));
    }
}