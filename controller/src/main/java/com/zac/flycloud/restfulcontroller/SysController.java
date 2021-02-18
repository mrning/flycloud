package com.zac.flycloud.restfulcontroller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zac.flycloud.entity.tablemodel.SysPermission;
import com.zac.flycloud.sys.SysPermissionService;
import com.zac.flycloud.utils.*;
import com.zac.flycloud.base.SysBaseAPI;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.constant.CacheConstant;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.entity.SysUserLoginVO;
import com.zac.flycloud.entity.tablemodel.SysDept;
import com.zac.flycloud.entity.tablemodel.SysUser;
import com.zac.flycloud.log.SysLogService;
import com.zac.flycloud.sys.SysDeptService;
import com.zac.flycloud.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.zac.flycloud.constant.CommonConstant.TOKEN_EXPIRE_TIME;

/**
 * 系统相关接口  登录/登出/注册/重置密码等
 *
 * @Author zac
 * @Date 20200702
 */
@Api(tags = "系统相关")
@RestController
@RequestMapping("/api/sys")
@Slf4j
public class SysController {

    @Autowired
    private SysBaseAPI sysBaseAPI;
    @Autowired
    private SysLogService logService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${flycloud.security.tokenKey}")
    private String tokenKey;

    /**
     * 登录接口
     *
     * @param sysLoginModel
     * @return
     */
    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public DataResponseResult<JSONObject> login(@RequestBody SysUserLoginVO sysLoginModel) {
        DataResponseResult<JSONObject> result = new DataResponseResult<JSONObject>();
        // 用户名
        String username = sysLoginModel.getUsername();
        // 密码
        String password = sysLoginModel.getPassword();
        // 验证码
        String captcha = sysLoginModel.getCaptcha();
        if (captcha == null) {
            result.error500("验证码无效");
            return result;
        }
        String lowerCaseCaptcha = captcha.toLowerCase();
        String realKey = MD5Util.MD5Encode(lowerCaseCaptcha + sysLoginModel.getCheckKey(), "utf-8");
        Object checkCode = redisUtil.get(realKey);
        if (checkCode == null || !checkCode.equals(lowerCaseCaptcha)) {
            result.error500("验证码错误");
            return result;
        }

        //1. 校验用户是否有效
        SysUser sysUser = sysUserService.getUserByName(username);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        //2. 校验密码是否正确
        String sysPassword = sysUser.getPassword();
        if (!PasswordUtil.getPasswordMatch(password, sysPassword)) {
            result.error500("用户名或密码错误");
            return result;
        }

        //用户登录信息
        try {
            userInfo(sysUser, result);
        } catch (Exception e) {
            log.error("登录异常", e);
            result.error500(e.getMessage());
        }
        // 记录操作日志
        sysBaseAPI.addLog("用户名: " + username + ",登录成功！", CommonConstant.LOG_TYPE_1, null);

        return result;
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation("登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public DataResponseResult<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader("X-Access-Token");
        if (StringUtils.isEmpty(token)) {
            return DataResponseResult.error("退出登录失败！");
        }
        String username = String.valueOf(redisUtil.get(token));
        SysUser sysUser = sysBaseAPI.getUserByName(username);
        if (sysUser != null) {
            sysBaseAPI.addLog("用户名: " + sysUser.getRealname() + ",退出成功！", CommonConstant.LOG_TYPE_1, null);
            log.info(" 用户名:  " + sysUser.getRealname() + ",退出成功！ ");
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));
            return DataResponseResult.success("退出登录成功！");
        } else {
            return DataResponseResult.error("Token无效!");
        }
    }

    /**
     * 用户注册接口
     *
     * @param jsonObject
     * @param user
     * @return
     */
    @ApiOperation("注册")
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
        if (StringUtils.isBlank(username)) {
            result.setMessage("用户名不能为空");
            result.setSuccess(false);
            return result;
        }
        //未设置密码
        if (StringUtils.isBlank(password)) {
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
        if (StringUtils.isNotBlank(email)) {
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
            DataResponseResult.success("注册成功");
        } catch (Exception e) {
            result.error500("注册失败");
        }
        return result;
    }


    /**
     * 校验用户账号是否唯一<br>
     * 可以校验其他 需要检验什么就传什么。。。
     *
     * @param sysUser
     * @return
     */
    @ApiOperation("校验账户唯一")
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
    @ApiOperation("修改密码")
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
     * 查询用户拥有的菜单权限
     *
     * @return
     */
    @ApiOperation("获取用户权限")
    @RequestMapping(value = "/getPermissionList", method = RequestMethod.GET)
    public DataResponseResult<?> getPermissionList() {
        DataResponseResult<JSONObject> result = new DataResponseResult<JSONObject>();
        try {
            List<SysPermission> sysPermissions = sysPermissionService.list();
            //至少添加首页路由
            if (!hasIndexPage(sysPermissions)) {
                SysPermission indexMenu = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getUuid, "homepage")).get(0);
                sysPermissions.add(0, indexMenu);
            }
            JSONObject json = new JSONObject();
            JSONArray menujsonArray = new JSONArray();
            sysPermissions.sort(Comparator.comparingInt(SysPermission::getSortNo));
            this.getPermissionJsonArray(menujsonArray, sysPermissions, null);
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
     * 获取菜单JSON数组
     *
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
            if (json == null) {
                continue;
            }
            if (parentJson == null && StringUtils.isEmpty(tempPid)) {
                jsonArray.add(json);
                if (!permission.isLeaf()) {
                    getPermissionJsonArray(jsonArray, metaList, json);
                }
            } else if (parentJson != null && StringUtils.isNotEmpty(tempPid) && tempPid.equals(parentJson.getString("uuid"))) {
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
     *
     * @param permission
     * @return
     */
    private JSONObject getPermissionJsonObject(SysPermission permission) {
        JSONObject json = new JSONObject();
        // 类型(0：一级菜单 1：子菜单 2：按钮)
        if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
            return null;
        } else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_0) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_1)) {
            json.put("uuid", permission.getUuid());
            if (permission.isRoute()) {
                json.put("route", "1");// 表示生成路由
            } else {
                json.put("route", "0");// 表示不生成路由
            }
            // 判断url是否外链
            if (UrlIPUtils.isWWWHttpUrl(permission.getUrl())) {
                json.put("path", MD5Util.MD5Encode(permission.getUrl(), "utf-8"));
            } else {
                json.put("path", permission.getUrl());
            }
            // 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
            if (StringUtils.isNotBlank(permission.getComponentName())) {
                json.put("name", permission.getComponentName());
            } else {
                json.put("name", UrlIPUtils.urlToRouteName(permission.getUrl()));
            }
            // 是否隐藏路由，默认都是显示的
            if (permission.isHidden()) {
                json.put("hidden", true);
            }
            json.put("component", permission.getComponent());

            JSONObject meta = new JSONObject();
            // 由用户设置是否缓存页面 用布尔值
            meta.put("keepAlive", permission.isKeepAlive());
            // 外链菜单打开方式
            meta.put("internalOrExternal", permission.isInternalOrExternal());
            // 菜单名称
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
            // 如果是外链的话
            if (UrlIPUtils.isWWWHttpUrl(permission.getUrl())) {
                meta.put("url", permission.getUrl());
            }
            json.put("meta", meta);
        }

        return json;
    }

    /**
     * 判断是否授权首页
     *
     * @param metaList
     * @return
     */
    public boolean hasIndexPage(List<SysPermission> metaList) {
        boolean hasIndexMenu = false;
        for (SysPermission sysPermission : metaList) {
            if ("首页".equals(sysPermission.getName())) {
                hasIndexMenu = true;
                break;
            }
        }
        return hasIndexMenu;
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @ApiOperation("获取访问量")
    @GetMapping("loginfo")
    public DataResponseResult<Object> loginfo() {
        JSONObject obj = new JSONObject();
        // 获取一天的开始和结束时间
        Date dayStart = DateUtils.getTodayStart();
        Date dayEnd = DateUtils.getTodayEnd();
        // 获取系统访问记录
        Long totalVisitCount = logService.findTotalVisitCount();
        obj.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = logService.findTodayVisitCount(dayStart, dayEnd);
        obj.put("todayVisitCount", todayVisitCount);
        Long todayIp = logService.findTodayIp(dayStart, dayEnd);
        obj.put("todayIp", todayIp);
        return DataResponseResult.success(obj, "获取访问量成功");
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("visitInfo")
    @ApiOperation("获取最近一周访问数量/ip数量")
    public DataResponseResult<List<Map<String, Object>>> visitInfo() {
        DataResponseResult<List<Map<String, Object>>> result = new DataResponseResult<List<Map<String, Object>>>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        List<Map<String, Object>> list = logService.findVisitCount(dayStart, dayEnd);
        result.setResult(ConverUtil.toLowerCasePageList(list));
        return result;
    }

    /**
     * 手机号登录接口
     *
     * @param jsonObject
     * @return
     */
    @ApiOperation("手机号登录接口")
    @PostMapping("/phoneLogin")
    public DataResponseResult<JSONObject> phoneLogin(@RequestBody JSONObject jsonObject) throws Exception {
        String phone = jsonObject.getString("mobile");
        String smscode = jsonObject.getString("captcha");

        // 校验用户有效性
        SysUser sysUser = sysUserService.getUserByPhone(phone);
        DataResponseResult<JSONObject> result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        // 验证手机验证码
        Object code = redisUtil.get(phone);
        if (!smscode.equals(code)) {
            result.setMessage("手机验证码错误");
            return result;
        }
        // 获取用户信息
        userInfo(sysUser, result);
        // 添加日志
        sysBaseAPI.addLog("用户名: " + sysUser.getUsername() + ",登录成功！", CommonConstant.LOG_TYPE_1, null);

        return result;
    }


    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private void userInfo(SysUser sysUser, DataResponseResult<JSONObject> result) throws Exception {
        String username = sysUser.getUsername();
        // 登录信息记录到security
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成token
        String token = PasswordUtil.createToken(username, tokenKey);
        // 设置token缓存有效时间 1个小时
        redisUtil.set(token, username);
        redisUtil.expire(token, TOKEN_EXPIRE_TIME);
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, TOKEN_EXPIRE_TIME);

        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        List<SysDept> departs = sysDeptService.queryUserDeparts(sysUser.getUuid());
        obj.put("departs", departs);
        obj.put("token", token);
        obj.put("userInfo", sysUser);
        result.setResult(obj);
        DataResponseResult.success(result);
    }

    /**
     * 后台生成图形验证码 ：有效
     *
     * @param response
     * @param key
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/randomImage/{key}")
    public DataResponseResult<String> randomImage(HttpServletResponse response, @PathVariable String key) {
        DataResponseResult<String> res = new DataResponseResult<String>();
        try {
            String code = MD5Util.createCode(4);

            log.info("验证码 = " + code);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode + key, "utf-8");
            // 验证码5分钟有效
            redisUtil.set(realKey, lowerCaseCode, 60 * 5);
            String base64 = RandImageUtil.generate(code);
            res.setSuccess(true);
            res.setResult(base64);
        } catch (Exception e) {
            res.error500("获取验证码出错" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

}