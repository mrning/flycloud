package com.zac.admin.controller;

import com.zac.admin.beans.vos.request.SysDictPageRequest;
import com.zac.admin.service.SysDictService;
import com.zac.base.basebeans.PageResult;
import com.zac.base.basebeans.Result;
import com.zac.base.bizentity.SysDict;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AutoCreateFile 字典管理 
 * @date 2023年4月26日星期三
 * @author zac
 */
@Tag(name = "字典管理")
@RestController
@RequestMapping("/sysDict")
@Slf4j
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AdminSysDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * AutoCreateFile add
     * @date 2023年4月26日星期三
     * @author zac
     */
    @Operation(summary = "添加")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysDict sysDict) {
        return Result.success(sysDictService.add(sysDict));
    }

    /**
     * AutoCreateFile del
     * @date 2023年4月26日星期三
     * @author zac
     */
    @Operation(summary = "删除")
    @PostMapping("/del")
    public Result<Integer> del(@RequestBody SysDict sysDict) {
        return Result.success(sysDictService.del(sysDict));
    }

    /**
     * AutoCreateFile update
     * @date 2023年4月26日星期三
     * @author zac
     */
    @Operation(summary = "修改")
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody SysDict sysDict) {
        return Result.success(sysDictService.update(sysDict));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年4月26日星期三
     * @author zac
     */
    @Operation(summary = "分页查询")
    @PostMapping("/queryPage")
    public Result<PageResult<SysDict>> queryPage(@RequestBody SysDictPageRequest pageRequest) {
        return Result.success(sysDictService.queryPage(pageRequest));
    }

    @Operation(summary = "查询全部")
    @PostMapping("/queryAll")
    public Result<List<SysDict>> queryAll(){
        return Result.success(sysDictService.queryAll());
    }

    @Operation(summary = "根据父级uuid查询子集列表")
    @PostMapping("/queryByParentUuid")
    public Result<List<SysDict>> queryByParentUuid(@RequestBody SysDictPageRequest dictRequest){
        Assert.notNull(dictRequest.getCode(), "父级编码不能为空");
        return Result.success(sysDictService.queryByParentUuid(dictRequest.getCode()));
    }
}