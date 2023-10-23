package com.lqjk.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lqjk.admin.beans.dtos.TreeModel;
import com.lqjk.admin.service.SysPermissionService;
import com.lqjk.base.bizentity.SysPermission;
import com.lqjk.base.bizentity.SysRole;
import com.lqjk.admin.beans.vos.request.RoleAddRequest;
import com.lqjk.admin.beans.vos.request.RoleRequest;
import com.lqjk.admin.beans.vos.request.RoleUpdateRequest;
import com.lqjk.admin.beans.vos.response.RolePageResponse;
import com.lqjk.admin.service.SysRoleService;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.base.basebeans.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AutoCreateFile 角色管理 
 * @date 2021年4月30日星期五
 * @author zac
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/sysRole")
@Slf4j
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@RequiredArgsConstructor
public class AdminSysRoleController {
    private final SysRoleService sysRoleService;
    private final SysPermissionService sysPermissionService;
    /**
     * AutoCreateFile add
     *
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "添加角色")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody RoleAddRequest addRequest) {
        return Result.success(sysRoleService.add(addRequest));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "删除角色")
    @PostMapping("/del")
    public Result<Integer> del(@RequestBody SysRole sysRole) {
        return Result.success(sysRoleService.del(sysRole));
    }

    /**
     * AutoCreateFile update
     *
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "修改角色")
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        return Result.success(sysRoleService.update(roleUpdateRequest));
    }

    /**
     * AutoCreateFile queryPage
     *
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "分页查询")
    @PostMapping("/queryPage")
    public Result<PageResult<RolePageResponse>> queryPage(@RequestBody RoleRequest roleRequest) {
        return Result.success(sysRoleService.queryPage(roleRequest));
    }

    /**
     * 查询全部角色
     * @date 2021年4月30日星期五
     * @author zac
     */
    @Operation(summary = "查询全部")
    @PostMapping("/queryAll")
    public Result<List<SysRole>> queryAll() {
        return Result.success(sysRoleService.queryAll());
    }

    @Operation(summary = "根据用户uuid查询")
    @GetMapping("/queryByUserUuid")
    public Result<List<SysRole>> queryByUserUuid(@RequestParam String userUuid){
        return Result.success(sysRoleService.queryUserRoles(userUuid));
    }

    /**
     * 用户角色授权功能，查询菜单权限树
     * @param request
     * @return
     */
    @GetMapping(value = "/queryTreeList")
    @Operation(summary = "角色授权时查询菜单权限树")
    public Result<Map<String,Object>> queryTreeList(HttpServletRequest request) {
        Result<Map<String,Object>> result = new Result<>();
        //全部权限ids
        List<String> uuids = new ArrayList<>();
        try {
            LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
            query.eq(SysPermission::getDeleted, Boolean.FALSE);
            query.orderByAsc(SysPermission::getSortNo);
            List<SysPermission> list = sysPermissionService.list(query);
            for(SysPermission sysPer : list) {
                uuids.add(sysPer.getUuid());
            }
            List<TreeModel> treeList = new ArrayList<>();
            getTreeModelList(treeList, list, null);
            Map<String,Object> resMap = new HashMap(5);
            //全部树节点数据
            resMap.put("treeList", treeList);
            //全部树ids
            resMap.put("uuids", uuids);
            result.setResult(resMap);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    private void getTreeModelList(List<TreeModel> treeList,List<SysPermission> metaList,TreeModel temp) {
        for (SysPermission permission : metaList) {
            String tempPid = permission.getParentUuid();
            TreeModel tree = new TreeModel(permission.getUuid(), tempPid, permission.getName(), permission.isLeaf());
            if(temp==null && StringUtils.isBlank(tempPid)) {
                treeList.add(tree);
                if(!tree.getIsLeaf()) {
                    getTreeModelList(treeList, metaList, tree);
                }
            }else if(temp!=null && tempPid!=null && tempPid.equals(temp.getKey())){
                temp.getChildren().add(tree);
                if(!tree.getIsLeaf()) {
                    getTreeModelList(treeList, metaList, tree);
                }
            }

        }
    }
}