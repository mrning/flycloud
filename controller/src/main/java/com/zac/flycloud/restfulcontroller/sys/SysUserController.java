package com.zac.flycloud.restfulcontroller.sys;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zac.flycloud.base.SysBaseAPI;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.entity.tablemodel.SysPermission;
import com.zac.flycloud.entity.tablemodel.SysUser;
import com.zac.flycloud.sys.SysDeptService;
import com.zac.flycloud.sys.SysPermissionService;
import com.zac.flycloud.sys.SysUserService;
import com.zac.flycloud.utils.MD5Util;
import com.zac.flycloud.utils.PasswordUtil;
import com.zac.flycloud.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 用户注册接口
     *
     * @param jsonObject
     * @param user
     * @return
     */
    @PostMapping("/register")
    public DataResponseResult<JSONObject> userRegister(@RequestBody JSONObject jsonObject, SysUser user) {
        DataResponseResult<JSONObject> result = new DataResponseResult<JSONObject>();
        // 手机号
        String phone = jsonObject.getString("phone");
        // 用户输入的短信验证码
        String smscode = jsonObject.getString("smscode");
        // 后台实际发送的短信验证码
        Object code = redisUtil.get(phone);
        // 用户名
        String username = jsonObject.getString("username");
        // 密码
        String password = jsonObject.getString("password");
        // 邮箱
        String email = jsonObject.getString("email");

        //未设置用户名
        if(StringUtils.isBlank(username)){
            result.setMessage("用户名不能为空");
            result.setSuccess(false);
            return result;
        }
        //未设置密码
        if(StringUtils.isBlank(password)){
            result.setMessage("密码不能为空");
            result.setSuccess(false);
            return result;
        }
        SysUser sysUser1 = sysUserService.getUserByName(username);
        if (sysUser1 != null) {
            result.setMessage("用户名已注册");
            result.setSuccess(false);
            return result;
        }
        SysUser sysUser2 = sysUserService.getUserByPhone(phone);
        if (sysUser2 != null) {
            result.setMessage("该手机号已注册");
            result.setSuccess(false);
            return result;
        }
        if (!smscode.equals(code)) {
            result.setMessage("手机验证码错误");
            result.setSuccess(false);
            return result;
        }
        //校验邮箱
        if(StringUtils.isNotBlank(email)){
            SysUser sysUser3 = sysUserService.getUserByEmail(email);
            if (sysUser3 != null) {
                result.setMessage("邮箱已被注册");
                result.setSuccess(false);
                return result;
            }
        }

        try {
            user.setCreateTime(new Date());// 设置创建时间
            String passwordEncode = PasswordUtil.getPasswordEncode(password);
            user.setUsername(username);
            user.setRealname(username);
            user.setPassword(passwordEncode);
            user.setMail(email);
            user.setPhone(phone);
            DataResponseResult.ok("注册成功");
        } catch (Exception e) {
            result.error500("注册失败");
        }
        return result;
    }


    /**
	  *  校验用户账号是否唯一<br>
	  *  可以校验其他 需要检验什么就传什么。。。
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/checkOnlyUser", method = RequestMethod.GET)
    public DataResponseResult<Boolean> checkOnlyUser(SysUser sysUser) {
        DataResponseResult<Boolean> result = new DataResponseResult<>();
        //如果此参数为false则程序发生异常
        result.setResult(true);
        try {
            //通过传入信息查询新的用户信息
            SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>(sysUser));
            if (user != null) {
                result.setSuccess(false);
                result.setMessage("用户账号已存在");
                return result;
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    public DataResponseResult<?> changePassword(@RequestBody SysUser sysUser) {
        SysUser u = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, sysUser.getUsername()));
        if (u == null) {
            return DataResponseResult.error("用户不存在！");
        }
        sysUser.setId(u.getId());
        return sysUserService.changePassword(sysUser);
    }

	/**
	 * 首页用户重置密码
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
	public DataResponseResult<?> changPassword(@RequestBody JSONObject json) {
		String username = json.getString("username");
		String oldpassword = json.getString("oldpassword");
		String password = json.getString("password");
		String confirmpassword = json.getString("confirmpassword");
		SysUser user = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
		if(user==null) {
			return DataResponseResult.error("用户不存在！");
		}
		return sysUserService.resetPassword(username,oldpassword,password,confirmpassword);
	}
	
	/**
	 * 用户手机号验证
	 */
	@PostMapping("/phoneVerification")
	public DataResponseResult<String> phoneVerification(@RequestBody JSONObject jsonObject) {
        DataResponseResult<String> result = new DataResponseResult<String>();
		String phone = jsonObject.getString("phone");
		String smscode = jsonObject.getString("smscode");
		Object code = redisUtil.get(phone);
		if (!smscode.equals(code)) {
			result.setMessage("手机验证码错误");
			result.setSuccess(false);
			return result;
		}
		redisUtil.set(phone, smscode);
		result.setResult(smscode);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 用户更改密码
	 */
	@GetMapping("/passwordChange")
	public DataResponseResult<SysUser> passwordChange(@RequestParam(name="username")String username,
										  @RequestParam(name="password")String password,
			                              @RequestParam(name="smscode")String smscode,
			                              @RequestParam(name="phone") String phone) {
        DataResponseResult<SysUser> result = new DataResponseResult<SysUser>();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(smscode)  || StringUtils.isBlank(phone) ) {
            result.setMessage("重置密码失败！");
            result.setSuccess(false);
            return result;
        }

        SysUser sysUser=new SysUser();
        Object object= redisUtil.get(phone);
        if(null==object) {
        	result.setMessage("短信验证码失效！");
            result.setSuccess(false);
            return result;
        }
        if(!smscode.equals(object)) {
        	result.setMessage("短信验证码不匹配！");
            result.setSuccess(false);
            return result;
        }
        sysUser = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username).eq(SysUser::getPhone,phone));
        if (sysUser == null) {
            result.setMessage("未找到用户！");
            result.setSuccess(false);
            return result;
        } else {
            String passwordEncode = PasswordUtil.getPasswordEncode(password);
            sysUser.setPassword(passwordEncode);
            this.sysUserService.updateById(sysUser);
            result.setSuccess(true);
            result.setMessage("密码重置完成！");
            return result;
        }
    }

    /**
     * 查询用户拥有的菜单权限和按钮权限（根据TOKEN）
     *
     * @return
     */
    @RequestMapping(value = "/getPermissionList", method = RequestMethod.GET)
    public DataResponseResult<?> getPermissionList() {
        DataResponseResult<JSONObject> result = new DataResponseResult<JSONObject>();
        try {
            List<SysPermission> metaList = sysPermissionService.list();
            //添加首页路由
            if(!hasIndexPage(metaList)){
                SysPermission indexMenu = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getName,"首页")).get(0);
                metaList.add(0,indexMenu);
            }
            JSONObject json = new JSONObject();
            JSONArray menujsonArray = new JSONArray();
            this.getPermissionJsonArray(menujsonArray, metaList, null);
            //路由菜单
            json.put("menu", menujsonArray);
            result.setResult(json);
            result.setMessage("查询成功");
        } catch (Exception e) {
            result.error500("查询失败:" + e.getMessage());
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     *  获取菜单JSON数组
     * @param jsonArray
     * @param metaList
     * @param parentJson
     */
    private void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> metaList, JSONObject parentJson) {
        for (SysPermission permission : metaList) {
            if (permission.getMenuType() == null) {
                continue;
            }
            String tempPid = permission.getParentId();
            JSONObject json = getPermissionJsonObject(permission);
            if(json==null) {
                continue;
            }
            if (parentJson == null && StringUtils.isEmpty(tempPid)) {
                jsonArray.add(json);
                if (!permission.isLeaf()) {
                    getPermissionJsonArray(jsonArray, metaList, json);
                }
            } else if (parentJson != null && StringUtils.isNotEmpty(tempPid) && tempPid.equals(parentJson.getString("id"))) {
                // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
                    JSONObject metaJson = parentJson.getJSONObject("meta");
                    if (metaJson.containsKey("permissionList")) {
                        metaJson.getJSONArray("permissionList").add(json);
                    } else {
                        JSONArray permissionList = new JSONArray();
                        permissionList.add(json);
                        metaJson.put("permissionList", permissionList);
                    }
                    // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                } else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_1) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_0)) {
                    if (parentJson.containsKey("children")) {
                        parentJson.getJSONArray("children").add(json);
                    } else {
                        JSONArray children = new JSONArray();
                        children.add(json);
                        parentJson.put("children", children);
                    }

                    if (!permission.isLeaf()) {
                        getPermissionJsonArray(jsonArray, metaList, json);
                    }
                }
            }

        }
    }

    /**
     * 根据菜单配置生成路由json
     * @param permission
     * @return
     */
    private JSONObject getPermissionJsonObject(SysPermission permission) {
        JSONObject json = new JSONObject();
        // 类型(0：一级菜单 1：子菜单 2：按钮)
        if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
            //json.put("action", permission.getPerms());
            //json.put("type", permission.getPermsType());
            //json.put("describe", permission.getName());
            return null;
        } else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_0) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_1)) {
            json.put("id", permission.getId());
            if (permission.isRoute()) {
                json.put("route", "1");// 表示生成路由
            } else {
                json.put("route", "0");// 表示不生成路由
            }

            if (isWWWHttpUrl(permission.getUrl())) {
                json.put("path", MD5Util.MD5Encode(permission.getUrl(), "utf-8"));
            } else {
                json.put("path", permission.getUrl());
            }

            // 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
            if (StringUtils.isNotBlank(permission.getComponentName())) {
                json.put("name", permission.getComponentName());
            } else {
                json.put("name", urlToRouteName(permission.getUrl()));
            }

            // 是否隐藏路由，默认都是显示的
            if (permission.isHidden()) {
                json.put("hidden", true);

            }
            // 聚合路由
            if (permission.isAlwaysShow()) {
                json.put("alwaysShow", true);
            }
            json.put("component", permission.getComponent());
            JSONObject meta = new JSONObject();
            // 由用户设置是否缓存页面 用布尔值
            meta.put("keepAlive", permission.isKeepAlive());

            /*update_begin author:wuxianquan date:20190908 for:往菜单信息里添加外链菜单打开方式 */
            //外链菜单打开方式
            meta.put("internalOrExternal", permission.isInternalOrExternal());
            /* update_end author:wuxianquan date:20190908 for: 往菜单信息里添加外链菜单打开方式*/

            meta.put("title", permission.getName());
            if (StringUtils.isEmpty(permission.getParentId())) {
                // 一级菜单跳转地址
                json.put("redirect", permission.getRedirect());
                if (StringUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            } else {
                if (StringUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            }
            if (isWWWHttpUrl(permission.getUrl())) {
                meta.put("url", permission.getUrl());
            }
            json.put("meta", meta);
        }

        return json;
    }

    /**
     * 判断是否外网URL 例如： http://localhost:8080/jeecg-boot/swagger-ui.html#/ 支持特殊格式： {{
     * window._CONFIG['domianURL'] }}/druid/ {{ JS代码片段 }}，前台解析会自动执行JS代码片段
     *
     * @return
     */
    private boolean isWWWHttpUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("{{"));
    }

    /**
     * 通过URL生成路由name（去掉URL前缀斜杠，替换内容中的斜杠‘/’为-） 举例： URL = /isystem/role RouteName =
     * isystem-role
     *
     * @return
     */
    private String urlToRouteName(String url) {
        if (StringUtils.isNotEmpty(url)) {
            if (url.startsWith("/")) {
                url = url.substring(1);
            }
            url = url.replace("/", "-");

            // 特殊标记
            url = url.replace(":", "@");
            return url;
        } else {
            return null;
        }
    }


    /**
     * 判断是否授权首页
     * @param metaList
     * @return
     */
    public boolean hasIndexPage(List<SysPermission> metaList){
        boolean hasIndexMenu = false;
        for (SysPermission sysPermission : metaList) {
            if("首页".equals(sysPermission.getName())) {
                hasIndexMenu = true;
                break;
            }
        }
        return hasIndexMenu;
    }

}
