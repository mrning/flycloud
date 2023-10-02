package com.lqjk.admin.controller;

import cn.hutool.core.lang.Assert;
import com.lqjk.admin.entity.SysDept;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.admin.beans.dtos.TreeDto;
import com.lqjk.admin.beans.vos.request.DeptRequest;
import com.lqjk.admin.service.SysDeptService;
import com.lqjk.base.basebeans.Result;
import com.lqjk.request.res.SysDeptResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RestController
@RequestMapping("/sysDept")
@Slf4j
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AdminSysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * AutoCreateFile add
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.add(sysDept));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月30日星期五
     * @author zac
     */
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
    @PostMapping("/queryPage")
    public Result<PageResult<SysDeptResponse>> queryPage(@RequestBody DeptRequest deptRequest) {
        return Result.success(sysDeptService.queryPage(deptRequest));
    }

    /**
     * 查询全部部门
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/queryAll")
    public Result<List<SysDept>> queryAll() {
        return Result.success(sysDeptService.queryAll());
    }

    @GetMapping("/queryByUserUuid")
    public Result<List<SysDept>> queryByUserUuid(@RequestParam String userUuid){
        return Result.success(sysDeptService.queryUserDeparts(userUuid));
    }

    @PostMapping("/getDeptUsers")
    public Result<List<TreeDto>> getDeptUsers(){
        return Result.success(sysDeptService.getDeptUsers());
    }
}