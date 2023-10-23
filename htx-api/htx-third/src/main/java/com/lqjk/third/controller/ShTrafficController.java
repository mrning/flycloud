package com.lqjk.third.controller;

import cn.hutool.json.JSONUtil;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.utils.RedisUtil;
import com.lqjk.mq.constants.MQConstant;
import com.lqjk.mq.service.RabbitMqService;
import com.lqjk.third.beans.*;
import com.lqjk.third.service.ShTrafficService;
import com.lqjk.third.utils.Sm3Util;
import com.lqjk.third.utils.Sm4Util;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 上海交通卡APP控制类
 * @Author YJD
 * @Date 2023/4/6 13:48
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/trafficardapi/api/shTraffic")
@Slf4j
public class ShTrafficController {

    @Value("${shTraffic.sm3Key}")
    private String sm3Key;
    @Value("${shTraffic.sm4Key}")
    private String sm4Key;

    @Autowired
    private ShTrafficService shTrafficService;

    @Autowired
    private RabbitMqService rabbitMqService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户授权推送
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/authUser")
    public Result<String> authUser(@RequestBody ShTrafficVo jsonStr) {
        String bodyJson = getDecryptBody(jsonStr);
        if (StringUtil.isNullOrEmpty(bodyJson)) {
            return Result.error(ShTrafficEnum.SIGN_FAILED.getCode(), ShTrafficEnum.SIGN_FAILED.getDesc());
        }
        AuthDataVo authData = JSONUtil.toBean(bodyJson, AuthDataVo.class);
        return switch (authData.getTransType()) {
            case AuthTransType.AUTH -> auth(authData);
            case AuthTransType.AUTH_CANCEL -> authCancel(authData);
            case AuthTransType.AUTH_MODIFY -> authModify(authData);
            default -> Result.error(ShTrafficEnum.FAILED.getCode(), ShTrafficEnum.FAILED.getDesc());
        };
    }
    /**
     * 用户授权推送(V1.3)
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/userAuthChange")
    public Result<String> userAuthChange(@RequestBody ShTrafficVo jsonStr){
        String bodyJson = getDecryptBody(jsonStr);
        if (StringUtil.isNullOrEmpty(bodyJson)) {
            return Result.error(ShTrafficEnum.SIGN_FAILED.getCode(), ShTrafficEnum.SIGN_FAILED.getDesc());
        }
        UserAuthData authData = JSONUtil.toBean(bodyJson, UserAuthData.class);
        if(StringUtil.isNullOrEmpty(authData.getUserToken())||StringUtil.isNullOrEmpty(authData.getMobile())){
            return Result.error(ShTrafficEnum.PARAMETER_ERROR.getCode(),ShTrafficEnum.PARAMETER_ERROR.getDesc());
        }
        AuthUserVo authUser = new AuthUserVo();
        authUser.setUserToken(authData.getUserToken());
        authUser.setMobile(authData.getMobile());
        switch (authData.getStatus()) {
            case AuthTransType.AUTH:
                return authV13(authUser);
            case AuthTransType.AUTH_CANCEL:
                return authCancelV13(authUser);
            default:
                return Result.error(ShTrafficEnum.FAILED.getCode(),ShTrafficEnum.FAILED.getDesc());
        }
    }


    //变更卡号
    private Result<String> authModify(AuthDataVo authData) {
        return Result.success(JSONUtil.toJsonStr(shTrafficService.authModify(authData.getAuthList())));
    }

    //授权
    private Result<String> auth(AuthDataVo authData) {
        AuthUserVo authUser = authData.getAuthList().get(0);
        boolean lock = redisUtil.tryLock(String.format("shTraffic.auth:%s", authUser.getUserToken()), "1", 10L);
        if (!lock) {
            return Result.error(ShTrafficEnum.REPEATED.getCode(), ShTrafficEnum.REPEATED.getDesc());
        }
        List<String> cardNoList = new ArrayList<String>();
        for (AuthUserVo user : authData.getAuthList()) {
            cardNoList.add(user.getCardNo());
        }
        return Result.success(shTrafficService.authorization(authUser, cardNoList));
    }

    //授权
    private Result<String> authV13(AuthUserVo authUser) {
        boolean lock = redisUtil.tryLock(String.format("shTraffic.auth:%s", authUser.getUserToken()), "1", 10L);
        if (!lock) {
            log.warn("上海授权业务正在处理中,请稍后!");
            return Result.error(ShTrafficEnum.REPEATED.getCode(),ShTrafficEnum.REPEATED.getDesc());
        }
        return Result.success(shTrafficService.authorization(authUser, null));
    }

    //取消授权
    private Result<String> authCancel(AuthDataVo authData) {
        AuthUserVo authUser = authData.getAuthList().get(0);
        return Result.success(JSONUtil.toJsonStr(shTrafficService.cancelAuth(authUser)));
    }

    //取消授权
    private Result<String> authCancelV13(AuthUserVo authUser) {
        return Result.success(JSONUtil.toJsonStr(shTrafficService.cancelAuth(authUser)));
    }


    /**
     * 用户行程推送
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/userTravel")
    public Result<String> uploadTradeInfo(@RequestBody ShTrafficVo jsonStr) {
        String bodyJson = getDecryptBody(jsonStr);
        if (StringUtil.isNullOrEmpty(bodyJson)) {
            return Result.error(ShTrafficEnum.SIGN_FAILED.getCode(),ShTrafficEnum.SIGN_FAILED.getDesc());
        }
        rabbitMqService.send(MQConstant.SHTRAFFIC_USER_TRAVEL_PUSH, bodyJson);
        return Result.success(ShTrafficEnum.SUCCESS.getCode(), ShTrafficEnum.SUCCESS.getDesc());
    }

    @RequestMapping(value = "/getAuthUser")
    @CrossOrigin(origins = "*")
    public Result<String> getAuthUser(@RequestBody ShTrafficVo shTrafficVo){
        String token = shTrafficVo.getToken();
        log.info("进入获取用户授权接口token：{}",token);
        boolean lock = redisUtil.tryLock(String.format("shTraffic.auth:%s", token), "1", 10L);
        if (!lock) {
            log.warn("上海授权业务正在处理中,请稍后!");
            return Result.error("03","授权用户推送中");
        }
        shTrafficService.getOwnerIdByToken(token);
        return Result.success();
    }

    /**
     * 解密操作
     *
     * @param jsonStr json字符串
     * @return 明文json
     */
    private String getDecryptBody(ShTrafficVo jsonStr) {
        String body = jsonStr.getBody();
        String sign = jsonStr.getSign();
        log.info("上海交通卡APP推送信息-body:" + body);
        log.info("上海交通卡APP推送信息-sign:" + sign);
        try {
            if (!Sm3Util.verify(body + sm3Key, sign)) {
                return null;
            }
            String decryptStr = Sm4Util.decryptEcb(sm4Key, body);
            log.info("解密后数据：{}",decryptStr);
            return decryptStr;
        } catch (Exception e) {
            log.error("验签失败");
            return null;
        }
    }
}
