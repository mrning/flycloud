package com.zac.admin.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zac.admin.beans.vos.request.RegisRequest;
import com.zac.admin.service.SysToolService;
import com.zac.admin.service.SysUserService;
import com.zac.admin.service.UploadFileService;
import com.zac.admin.service.impl.ossImpl.AliOssServiceImpl;
import com.zac.admin.service.impl.ossImpl.TencentServiceImpl;
import com.zac.base.basebeans.Result;
import com.zac.base.bizentity.SysUser;
import com.zac.base.enums.UploadClientEnum;
import com.zac.base.utils.*;
import com.zac.request.FeignResult;
import com.zac.request.req.auth.AuthLoginRequest;
import com.zac.request.req.auth.AuthPhoneLoginRequest;
import com.zac.security.annotation.InnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

/**
 * 系统相关接口  登录/登出/注册/重置密码等
 *
 * @Author zac
 * @Date 20200702
 */
@RestController
@RequestMapping("/sys")
@Tag(name = "系统管理")
@Slf4j
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@RequiredArgsConstructor
public class AdminSysController {

    private UploadFileService uploadFileService;

    private final RedisUtil redisUtil;

    private final SysUserService sysUserService;

    private final SysToolService sysToolService;

    @Value("${zacboot.uploadClient:OSS_TENCENT_CLIENT}")
    private String uploadClient;

    /**
     * 用户注册接口
     * 用户名和密码必输一个
     *
     * @param regisRequest
     * @return
     */
    @InnerService
    @Operation(summary = "用户注册接口")
    @PostMapping("/register")
    public Result userRegister(@RequestBody RegisRequest regisRequest) {
        Assert.isTrue(StringUtils.isNotBlank(regisRequest.getUsername()) || StringUtils.isNotBlank(regisRequest.getPhone()), "用户名不能为空");

        // 用户名注册
        if (StringUtils.isNotBlank(regisRequest.getUsername())) {
            Assert.isTrue(null == sysUserService.getUserByName(regisRequest.getUsername()), "用户名已注册");
            Assert.notNull(regisRequest.getPassword(), "密码不能为空");
            Assert.notNull(regisRequest.getMail(), "邮箱不能为空");
            // 校验邮箱
            if (StringUtils.isNotBlank(regisRequest.getMail())) {
                Assert.isTrue(null == sysUserService.getUserByEmail(regisRequest.getMail()), "邮箱已被注册");
            }
        }
        // 手机号注册
        if (StringUtils.isNotBlank(regisRequest.getPhone())) {
            Assert.notNull(regisRequest.getSmscode(), "验证码不能为空");
            Assert.isTrue(null == sysUserService.getUserByPhone(regisRequest.getPhone()), "用户名已注册");
            // 校验验证码
            Object code = redisUtil.get(regisRequest.getPhone());
            Assert.isTrue(regisRequest.getSmscode().equals(code), "手机验证码错误");
            regisRequest.setUsername(regisRequest.getPhone());
        }
        if (sysUserService.register(regisRequest)) {
            return Result.success("注册成功");
        }
        return Result.error("注册失败");
    }

    /**
     * 用户名密码登录接口
     */
    @InnerService
    @Operation(summary = "用户名密码登录接口")
    @PostMapping(value = "/login")
    public Result<JSONObject> login(@RequestBody AuthLoginRequest authLoginRequest) {
        Result<JSONObject> result;
        // 用户名
        String username = authLoginRequest.getUsername();
        // 密码
        String password = authLoginRequest.getPassword();

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
     * @param phoneLoginRequest
     * @return
     */
    @InnerService
    @Operation(summary = "手机号登录接口")
    @PostMapping("/phoneLogin")
    public Result<JSONObject> phoneLogin(@RequestBody AuthPhoneLoginRequest phoneLoginRequest) {
        // 校验用户有效性
        SysUser sysUser = sysUserService.getUserByPhone(phoneLoginRequest.getMobile());
        Result<JSONObject> result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        // 验证手机验证码
        Object code = redisUtil.get(phoneLoginRequest.getMobile());
        if (!phoneLoginRequest.getCaptcha().equals(code)) {
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
    @Operation(summary = "退出登录")
    @PostMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request) {
        //用户退出逻辑
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return Result.error("token为空，退出登录失败！");
        }
        return Result.success(sysUserService.logout(token));
    }

    /**
     * 校验用户账号是否唯一<br>
     * 可以校验其他 需要检验什么就传什么。。。
     *
     * @param sysUser
     * @return
     */
    @Operation(summary = "校验用户账号是否唯一")
    @GetMapping(value = "/checkOnlyUser")
    public Result<Boolean> checkOnlyUser(@RequestBody SysUser sysUser) {
        Result<Boolean> result = new Result<>();
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
            log.error("checkOnlyUser error", e);
        }
        result.setSuccess(true);
        return result;
    }

    /**
     * 修改密码
     */
    @Operation(summary = "修改密码")
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
    @InnerService
    @Operation(summary = "后台生成图形验证码")
    @GetMapping(value = "/randomImage")
    public Result<String> randomImage(@RequestParam String key) {
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
    @Operation(summary = "文件上传统一方法")
    @PostMapping(value = "/upload")
    public Result<String> upload(MultipartRequest multipartRequest, String savePath) {
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
    @InnerService
    @Operation(summary = "查询指定网址内的新闻刷新情况")
    @PostMapping("/checkNews")
    public FeignResult<String> checkNews(){
        return FeignResult.success(sysToolService.checkNews());
    }
}