package com.zacboot.autocode.api.admin;

import cn.hutool.db.PageResult;
import com.zacboot.autocode.BaseController;
import com.zacboot.autocode.basebean.Result;
import com.zacboot.autocode.dto.SysDept;
import com.zacboot.autocode.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile 部门管理 
 * @date 2022年12月10日星期六
 * @author zac
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/api/admin/sysDept")
@Slf4j
public class AdminSysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * AutoCreateFile add
     * @date 2022年12月10日星期六
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.add(sysDept));
    }

    /**
     * AutoCreateFile del
     * @date 2022年12月10日星期六
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.del(sysDept));
    }

    /**
     * AutoCreateFile update
     * @date 2022年12月10日星期六
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.update(sysDept));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2022年12月10日星期六
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<SysDept>> queryPage(@RequestBody SysDept sysDept) {
        return Result.success(sysDeptService.queryPage(sysDept));
    }
}