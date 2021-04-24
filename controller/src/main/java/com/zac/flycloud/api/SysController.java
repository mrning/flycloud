package com.zac.flycloud.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.constant.CacheConstant;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.sys.SysUserService;
import com.zac.flycloud.tablemodel.SysUser;
import com.zac.flycloud.utils.MD5Util;
import com.zac.flycloud.utils.PasswordUtil;
import com.zac.flycloud.utils.RandImageUtil;
import com.zac.flycloud.utils.RedisUtil;
import com.zac.flycloud.vos.SysUserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    private RedisUtil redisUtil;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户名密码登录接口
     *
     * @param sysLoginModel
     * @return
     */
    @ApiOperation("用户名密码登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public DataResponseResult<Object> login(@RequestBody SysUserLoginVO sysLoginModel) {
        DataResponseResult<Object> result = DataResponseResult.success();
        // 用户名
        String username = sysLoginModel.getUsername();
        // 密码
        String password = sysLoginModel.getPassword();
        // 验证码
        String captcha = sysLoginModel.getCaptcha();

        // 1. 校验验证码
        if (StringUtils.isBlank(captcha)) {
            result.error500("验证码不能为空");
            return result;
        }
        String lowerCase = captcha.toLowerCase();
        Object checkCode = redisUtil.get(MD5Util.MD5Encode(lowerCase + sysLoginModel.getCheckKey(), "utf-8"));
        if (checkCode == null || !checkCode.equals(lowerCase)) {
            result.error500("验证码错误");
            return result;
        }

        // 2. 校验用户是否有效
        SysUser sysUser = sysUserService.getUserByName(username);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        // 3. 校验密码是否正确
        String sysPassword = sysUser.getPassword();
        if (!PasswordUtil.getPasswordMatch(password, sysPassword)) {
            result.error500("用户名或密码错误");
            return result;
        }

        // 4. 获取用户登录信息
        try {
            result.setResult(sysUserService.userInfo(sysUser));
        } catch (Exception e) {
            log.error("登录异常", e);
            result.error500(e.getMessage());
        }

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

        result.setResult(sysUserService.userInfo(sysUser));

        return result;
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @ApiOperation("登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public DataResponseResult<Object> logout(HttpServletRequest request) {
        //用户退出逻辑
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return DataResponseResult.error("token为空，退出登录失败！");
        }
        String username = String.valueOf(redisUtil.get(token));
        SysUser sysUser = sysUserService.getUserByName(username);
        if (sysUser != null) {
            //清空用户登录Token缓存
            redisUtil.del(token);
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));

            sysUserService.addLog("用户名: " + sysUser.getRealname() + ",退出成功！", CommonConstant.LOG_TYPE_LOGIN_1, null);
            log.info(" 用户名:  " + sysUser.getRealname() + ",退出成功！ ");
        }
        return DataResponseResult.success("退出登录成功！");
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
            SysUser user = (SysUser) sysUserService.getOne(new QueryWrapper<>(sysUser));
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
        SysUser u =  (SysUser) sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, sysUser.getUsername()));
        if (u == null) {
            return DataResponseResult.error("用户不存在！");
        }
        sysUser.setId(u.getId());
        return sysUserService.changePassword(sysUser);
    }

    /**
     * 后台生成图形验证码 ：有效
     *
     * @param key
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/randomImage/{key}")
    public DataResponseResult<String> randomImage(@PathVariable String key) {
        DataResponseResult<String> res = new DataResponseResult<String>();
        try {
            String code = MD5Util.createCode(4);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode + key, "utf-8");
            // 验证码5分钟有效
            redisUtil.set(realKey, lowerCaseCode, 60 * 5);
            String base64 = RandImageUtil.generate(code);
            res.setMessage(code);
            res.setSuccess(true);
            res.setResult(base64);
        } catch (Exception e) {
            res.error500("获取验证码出错" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

}