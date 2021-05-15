package com.zac.flycloud.api.admin;

import cn.hutool.db.PageResult;
import com.zac.flycloud.BaseController;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.dto.SysUserDeptDTO;
import com.zac.flycloud.service.SysUserDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * AutoCreateFile 用户部门关联 
 * @date 2021年4月30日星期五
 * @author zac
 */
@Api(tags = "用户部门关联")
@RestController
@RequestMapping("/api/admin/sysUserDept")
@Slf4j
public class AdminSysUserDeptController extends BaseController {
    @Autowired
    private SysUserDeptService sysUserDeptService;

    /**
     * AutoCreateFile add
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/add")
    @ApiOperation("新增")
    public DataResponseResult<Integer> add(@RequestBody SysUserDeptDTO sysUserDeptDTO) {
        return DataResponseResult.success(sysUserDeptService.add(sysUserDeptDTO));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/del")
    @ApiOperation("删除")
    public DataResponseResult<Integer> del(@RequestBody SysUserDeptDTO sysUserDeptDTO) {
        return DataResponseResult.success(sysUserDeptService.del(sysUserDeptDTO));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public DataResponseResult<Integer> update(@RequestBody SysUserDeptDTO sysUserDeptDTO) {
        return DataResponseResult.success(sysUserDeptService.update(sysUserDeptDTO));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月30日星期五
     * @author zac
     */
    @PostMapping("/queryPage")
    @ApiOperation("分页查询")
    public DataResponseResult<PageResult<SysUserDeptDTO>> queryPage(@RequestBody SysUserDeptDTO sysUserDeptDTO) {
        return DataResponseResult.success(sysUserDeptService.queryPage(sysUserDeptDTO));
    }
}