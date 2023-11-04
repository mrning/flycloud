package com.zac.admin.controller;

import cn.hutool.core.lang.Assert;
import com.zac.base.bizentity.SysDept;
import com.zac.base.basebeans.PageResult;
import com.zac.admin.beans.dtos.TreeDto;
import com.zac.admin.beans.vos.request.DeptRequest;
import com.zac.admin.service.SysDeptService;
import com.zac.base.basebeans.Result;
import com.zac.request.res.SysDeptResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AutoCreateFile 部门管理 
 * @date 2021年4月30日星期五
 * @author zac
 */
@Slf4j
@RestController
@RequestMapping("/sysDept")
@Tag(name = "部门管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AdminSysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * AutoCreateFile add
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "添加部门")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.add(sysDept));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "删除部门")
    @PostMapping("/del")
    public Result<Integer> del(@RequestBody SysDept sysDept) {
        Assert.notNull(StringUtils.isNotBlank(sysDept.getUuid()), "删除失败");
        return Result.success(sysDeptService.del(sysDept));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "更新部门")
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.update(sysDept));
    }

    /**
     * AutoCreateFile queryPage
     *
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "分页查询")
    @PostMapping("/queryPage")
    public Result<PageResult<SysDeptResponse>> queryPage(@RequestBody DeptRequest deptRequest) {
        return Result.success(sysDeptService.queryPage(deptRequest));
    }

    /**
     * 查询全部部门
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "查询全部")
    @PostMapping("/queryAll")
    public Result<List<SysDept>> queryAll() {
        return Result.success(sysDeptService.queryAll());
    }

    @Operation(summary = "根据用户id查询部门列表")
    @GetMapping("/queryByUserUuid")
    public Result<List<SysDept>> queryByUserUuid(@RequestParam String userUuid){
        return Result.success(sysDeptService.queryUserDeparts(userUuid));
    }

    @Operation(summary = "查询部门列表树形列表")
    @PostMapping("/getDeptUsers")
    public Result<List<TreeDto>> getDeptUsers(){
        return Result.success(sysDeptService.getDeptUsers());
    }
}