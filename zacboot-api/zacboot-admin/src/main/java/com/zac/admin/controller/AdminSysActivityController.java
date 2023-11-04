package com.zac.admin.controller;

import com.zac.admin.beans.vos.request.SysActivityPageRequest;
import com.zac.admin.service.SysActivityService;
import com.zac.base.basebeans.PageResult;
import com.zac.base.basebeans.Result;
import com.zac.base.bizentity.SysActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile 活动管理 
 * @date 2023年10月30日星期一
 * @author zac
 */
@Tag(name = "活动管理")
@RestController
@RequestMapping("/api/admin/sysActivity")
@Slf4j
public class AdminSysActivityController {
    @Autowired
    private SysActivityService sysActivityService;

    /**
     * AutoCreateFile add
     * @date 2023年10月30日星期一
     * @author zac
     */
    @PostMapping("/add")
    @Operation(summary = "添加")
    public Result<Integer> add(@RequestBody SysActivity sysActivity) {
        return Result.success(sysActivityService.add(sysActivity));
    }

    /**
     * AutoCreateFile del
     * @date 2023年10月30日星期一
     * @author zac
     */
    @PostMapping("/del")
    @Operation(summary = "删除")
    public Result<Integer> del(@RequestBody SysActivity sysActivity) {
        return Result.success(sysActivityService.del(sysActivity));
    }

    /**
     * AutoCreateFile update
     * @date 2023年10月30日星期一
     * @author zac
     */
    @PostMapping("/update")
    @Operation(summary = "更新")
    public Result<Integer> update(@RequestBody SysActivity sysActivity) {
        return Result.success(sysActivityService.update(sysActivity));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年10月30日星期一
     * @author zac
     */
    @PostMapping("/queryPage")
    @Operation(summary = "分页查询，page默认为0")
    public Result<PageResult<SysActivity>> queryPage(@RequestBody SysActivityPageRequest pageRequest) {
        return Result.success(sysActivityService.queryPage(pageRequest));
    }
}