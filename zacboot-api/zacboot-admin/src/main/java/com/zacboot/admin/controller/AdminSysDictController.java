package com.zacboot.admin.controller;

import com.zacboot.admin.beans.vos.request.SysDictPageRequest;
import com.zacboot.admin.service.SysDictService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import com.zacboot.core.entity.admin.SysDict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AutoCreateFile 字典管理 
 * @date 2023年4月26日星期三
 * @author zac
 */
@Api(tags = "字典管理")
@RestController
@RequestMapping("/sysDict")
@Slf4j
public class AdminSysDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * AutoCreateFile add
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody SysDict sysDict) {
        return Result.success(sysDictService.add(sysDict));
    }

    /**
     * AutoCreateFile del
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody SysDict sysDict) {
        return Result.success(sysDictService.del(sysDict));
    }

    /**
     * AutoCreateFile update
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody SysDict sysDict) {
        return Result.success(sysDictService.update(sysDict));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<SysDict>> queryPage(@RequestBody SysDictPageRequest pageRequest) {
        return Result.success(sysDictService.queryPage(pageRequest));
    }

    @PostMapping("/queryAll")
    @ApiOperation("查询全部")
    public Result<List<SysDict>> queryAll(){
        return Result.success(sysDictService.queryAll());
    }
}