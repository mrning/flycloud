package com.zac.flycloud.controller.api.admin;

import com.zac.flycloud.bean.basebean.PageResult;
import com.zac.flycloud.bean.basebean.Result;
import com.zac.flycloud.bean.dto.SysUser;
import com.zac.flycloud.bean.vos.UserRequestVO;
import com.zac.flycloud.controller.BaseController;
import com.zac.flycloud.service.SysUserService;
import com.zac.flycloud.utils.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * AutoCreateFile 日志相关 
 * @date 2021年4月24日星期六
 * @author zac
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/admin/sysUser")
@Slf4j
public class AdminSysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * AutoCreateFile add
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("新增")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysUser sysUser) {
        sysUser.setPassword(PasswordUtil.getPasswordEncode(sysUser.getPassword()));
        return Result.success(sysUserService.add(sysUser));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("删除")
    @PostMapping("/del")
    public Result<Integer> del(@RequestBody SysUser sysUser) {
        return Result.success(sysUserService.del(sysUser));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("更新")
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody SysUser sysUser) {
        return Result.success(sysUserService.update(sysUser));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月24日星期六
     * @author zac
     */
    @ApiOperation("分页查询")
    @PostMapping("/queryPage")
    public Result<PageResult<SysUser>> queryPage(@RequestBody UserRequestVO userRequestVO) {
        return Result.success(sysUserService.queryPage(userRequestVO));
    }
}