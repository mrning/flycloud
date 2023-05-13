package com.zacboot.admin.work.controller;

import com.zacboot.admin.work.beans.request.AppUserMonthAssessPageRequest;
import com.zacboot.admin.work.service.AppUserMonthAssessService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.core.entity.assess.AppUserMonthAssess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile 月度工作内容考核 
 * @date 2023年4月23日星期日
 * @author zac
 */
@Api(tags = "月度工作内容考核")
@RestController
@RequestMapping("/api/admin/appUserMonthAssess")
@Slf4j
public class AppUserMonthAssessController {
    @Autowired
    private AppUserMonthAssessService appUserMonthAssessService;

    /**
     * AutoCreateFile add
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody AppUserMonthAssess appUserMonthAssess) {
        return Result.success(appUserMonthAssessService.add(appUserMonthAssess));
    }

    /**
     * AutoCreateFile del
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody AppUserMonthAssess appUserMonthAssess) {
        return Result.success(appUserMonthAssessService.del(appUserMonthAssess));
    }

    /**
     * AutoCreateFile update
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody AppUserMonthAssess appUserMonthAssess) {
        return Result.success(appUserMonthAssessService.update(appUserMonthAssess));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年4月23日星期日
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<AppUserMonthAssess>> queryPage(@RequestBody AppUserMonthAssessPageRequest pageRequest) {
        return Result.success(appUserMonthAssessService.queryPage(pageRequest));
    }
}