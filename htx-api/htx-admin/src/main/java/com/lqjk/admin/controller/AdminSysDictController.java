package com.lqjk.admin.controller;

import com.lqjk.admin.beans.vos.request.SysDictPageRequest;
import com.lqjk.admin.service.SysDictService;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.bizentity.SysDict;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpHeaders;
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
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysDict sysDict) {
        return Result.success(sysDictService.add(sysDict));
    }

    /**
     * AutoCreateFile del
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/del")
    public Result<Integer> del(@RequestBody SysDict sysDict) {
        return Result.success(sysDictService.del(sysDict));
    }

    /**
     * AutoCreateFile update
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody SysDict sysDict) {
        return Result.success(sysDictService.update(sysDict));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2023年4月26日星期三
     * @author zac
     */
    @PostMapping("/queryPage")
    public Result<PageResult<SysDict>> queryPage(@RequestBody SysDictPageRequest pageRequest) {
        return Result.success(sysDictService.queryPage(pageRequest));
    }

    @PostMapping("/queryAll")
    public Result<List<SysDict>> queryAll(){
        return Result.success(sysDictService.queryAll());
    }
}