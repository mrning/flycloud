package com.zac.flycloud.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zac.flycloud.admin.beans.constants.AdminConstants;
import com.zac.flycloud.admin.beans.entity.SysUser;
import com.zac.flycloud.admin.beans.vos.SysUserLoginVO;
import com.zac.flycloud.admin.beans.vos.request.RegisRequest;
import com.zac.flycloud.admin.service.SysUserService;
import com.zac.flycloud.admin.service.UploadFileService;
import com.zac.flycloud.admin.service.impl.ossImpl.AliOssServiceImpl;
import com.zac.flycloud.admin.service.impl.ossImpl.TencentServiceImpl;
import com.zac.flycloud.common.basebeans.Result;
import com.zac.flycloud.common.constants.CommonConstant;
import com.zac.flycloud.common.enums.UploadClientEnum;
import com.zac.flycloud.common.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

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
public class AdminSysController {

    private UploadFileService uploadFileService;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysUserService sysUserService;

    @Value("${flycloud.uploadClient}")
    private String uploadClient;

    /**
     * 用户名密码登录接口
     *
     * @param sysLoginModel
     * @return
     */
    @ApiOperation("用户名密码登录接口")
    @PostMapping(value = "/login")
    public Result<Object> login(@RequestBody SysUserLoginVO sysLoginModel) {
        Result<Object> result = Result.success();
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
    public Result<JSONObject> phoneLogin(@RequestBody JSONObject jsonObject) throws Exception {
        String phone = jsonObject.getString("mobile");
        String smscode = jsonObject.getString("captcha");

        // 校验用户有效性
        SysUser sysUser = sysUserService.getUserByPhone(phone);
        Result<JSONObject> result = sysUserService.checkUserIsEffective(sysUser);
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
    @PostMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request) {
        //用户退出逻辑
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return Result.error("token为空，退出登录失败！");
        }
        String username = String.valueOf(redisUtil.get(token));
        SysUser sysUser = sysUserService.getUserByName(username);
        if (sysUser != null) {
            //清空用户登录Token缓存
            redisUtil.del(token);
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s::%s", AdminConstants.SYS_USERS_CACHE, sysUser.getUsername()));

            sysUserService.addLog("用户名: " + sysUser.getRealname() + ",退出成功！", CommonConstant.LOG_TYPE_LOGIN_1, null);
            log.info(" 用户名:  " + sysUser.getRealname() + ",退出成功！ ");
        }
        return Result.success("退出登录成功！");
    }

    /**
     * 用户注册接口
     *
     * @param regisRequest
     * @return
     */
    @ApiOperation("注册")
    @PostMapping("/register")
    public Result userRegister(@RequestBody RegisRequest regisRequest) {
        Assert.notNull(regisRequest.getUsername(), "用户名不能为空");
        Assert.notNull(regisRequest.getPassword(), "密码不能为空");
        Assert.isTrue(null == sysUserService.getUserByName(regisRequest.getUsername()), "用户名已注册");
        // 校验验证码
        if (StringUtils.isNotBlank(regisRequest.getSmscode())) {
            // 后台实际发送的短信验证码
            Object code = redisUtil.get(regisRequest.getPhone());
            Assert.isTrue(regisRequest.getSmscode().equals(code), "手机验证码错误");
        }
        // 校验邮箱
        if (StringUtils.isNotBlank(regisRequest.getEmail())) {
            Assert.isTrue(null == sysUserService.getUserByEmail(regisRequest.getEmail()), "邮箱已被注册");
        }
        if (sysUserService.regis(regisRequest)) {
            return Result.success("注册成功");
        }
        return Result.error("注册失败");
    }

    /**
     * 校验用户账号是否唯一<br>
     * 可以校验其他 需要检验什么就传什么。。。
     *
     * @param sysUser
     * @return
     */
    @ApiOperation("校验账户唯一")
    @GetMapping(value = "/checkOnlyUser")
    public Result<Boolean> checkOnlyUser(SysUser sysUser) {
        Result<Boolean> result = new Result<>();
        //如果此参数为false则程序发生异常
        result.setResult(true);
        try {
            //通过传入信息查询新的用户信息
            SysUser user = sysUserService.getOne(new QueryWrapper<>(sysUser));
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
    @PutMapping(value = "/changePassword")
    public Result<?> changePassword(@RequestBody SysUser sysUser) {
        SysUser u = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, sysUser.getUsername()));
        if (u == null) {
            return Result.error("用户不存在！");
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
    public Result<String> randomImage(@PathVariable String key) {
        Result<String> res = new Result<String>();
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

    /**
     * 文件上传统一方法
     *
     * @return
     */
    @PostMapping(value = "/upload")
    public Result<String> upload(MultipartHttpServletRequest multipartRequest, String savePath) {
        Result<String> result = new Result<>();
        try {
            // 获取上传文件对象
            MultipartFile file = multipartRequest.getFile("file");

            UploadClientEnum clientEnum = UploadClientEnum.valueOf(uploadClient);
            switch (clientEnum) {
                case OSS_ALI_CLIENT:
                    uploadFileService = SpringContextUtils.getBean(AliOssServiceImpl.class);
                    break;
                case OSS_TENCENT_CLIENT:
                    uploadFileService = SpringContextUtils.getBean(TencentServiceImpl.class);
                    break;
            }

            JSONObject upload = uploadFileService.upload(file, savePath, null);

            result.setResult(upload.toJSONString());
        } catch (Exception e) {
            log.error("文件上传异常", e);
            result.error500("文件上传异常, " + e.getMessage());
        }
        return result;
    }
}