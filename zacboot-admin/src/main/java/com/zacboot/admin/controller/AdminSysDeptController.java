package com.zacboot.admin.controller;

import com.zacboot.admin.beans.entity.SysDept;
import com.zacboot.admin.beans.vos.request.DeptRequest;
import com.zacboot.admin.service.SysDeptService;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AutoCreateFile 部门管理 
 * @date 2021年4月30日星期五
 * @author zac
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/sysDept")
@Slf4j
public class AdminSysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * AutoCreateFile add
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.add(sysDept));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.del(sysDept));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.update(sysDept));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<SysDept>> queryPage(@RequestBody DeptRequest deptRequest) {
        return Result.success(sysDeptService.queryPage(deptRequest));
    }

    /**
     * 查询全部部门
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/queryAll")
    @ApiOperation("查询全部")
    public Result<List<SysDept>> queryAll() {
        return Result.success(sysDeptService.queryAll());
    }

    @GetMapping("/queryByUserUuid")
    @ApiOperation("根据用户uuid查询用户部门列表")
    public Result<List<SysDept>> queryByUserUuid(@RequestParam String userUuid){
        return Result.success(sysDeptService.queryUserDeparts(userUuid));
    }
}