package com.zacboot.admin.property.controller;

import com.zacboot.admin.property.dto.request.AppPropertyPageRequest;
import com.zacboot.admin.property.service.AppPropertyService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.system.core.entity.administration.AppProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile 固定资产
 * @date 2023年4月26日星期三
 * @author zac
 */
@Api(tags = "固定资产")
@RestController
@RequestMapping("/appProperty")
@Slf4j
public class AppPropertyController {
    @Autowired
    private AppPropertyService appPropertyService;

    /**
     * AutoCreateFile add
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody AppProperty appProperty) {
        return Result.success(appPropertyService.add(appProperty));
    }

    /**
     * AutoCreateFile del
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody AppProperty appProperty) {
        return Result.success(appPropertyService.del(appProperty));
    }

    /**
     * AutoCreateFile update
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody AppProperty appProperty) {
        return Result.success(appPropertyService.update(appProperty));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<AppProperty>> queryPage(@RequestBody AppPropertyPageRequest pageRequest) {
        return Result.success(appPropertyService.queryPage(pageRequest));
    }
}