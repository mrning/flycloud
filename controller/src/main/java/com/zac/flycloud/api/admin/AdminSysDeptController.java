package com.zac.flycloud.api.admin;

import com.zac.flycloud.basebean.PageResult;
import com.zac.flycloud.BaseController;
import com.zac.flycloud.basebean.Result;
import com.zac.flycloud.dto.SysDeptDTO;
import com.zac.flycloud.service.SysDeptService;
import com.zac.flycloud.vos.DeptRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile 部门管理 
 * @date 2021年4月30日星期五
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
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public Result<Integer> add(@RequestBody SysDeptDTO sysDeptDTO) {
        return Result.success(sysDeptService.add(sysDeptDTO));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public Result<Integer> del(@RequestBody SysDeptDTO sysDeptDTO) {
        return Result.success(sysDeptService.del(sysDeptDTO));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<Integer> update(@RequestBody SysDeptDTO sysDeptDTO) {
        return Result.success(sysDeptService.update(sysDeptDTO));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public Result<PageResult<SysDeptDTO>> queryPage(@RequestBody DeptRequestVO deptRequestVO) {
        return Result.success(sysDeptService.queryPage(deptRequestVO));
    }
}