package com.lqjk.third.controller;

import cn.hutool.json.JSONUtil;
import com.lqjk.base.basebeans.Result;
import com.lqjk.mq.constants.MQConstant;
import com.lqjk.mq.service.RabbitMqService;
import com.lqjk.third.beans.*;
import com.lqjk.third.service.AppThirdAuthService;
import com.lqjk.third.service.impl.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName T3TaxiController
 * @Description TODO
 * @Author YJD
 * @Date 2023/7/31 9:00
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/api/t3Taxi")
@Slf4j
public class T3TaxiController {

    @Autowired
    private RabbitMqService rabbitMqService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AppThirdAuthService appThirdAuthService;

    /**
     * 用户行程推送
     * @param t3CarTravelVo
     * @return
     */
    @RequestMapping(value = "/userTravel")
    public String uploadTradeInfo(@RequestBody T3CarTravelVo t3CarTravelVo) {

        CustomerLogin customerLogin = customerService.getCustomerInfoByPhone(t3CarTravelVo.getPhone());
        if(customerLogin==null){
            log.info("未查询到该用户");
            return JSONUtil.toJsonStr(Result.error(ShTrafficEnum.FAILED.getCode(),"未查询到该用户"));
        }
        AppThirdAuth authQuery = new AppThirdAuth();
        authQuery.setOwnerId(customerLogin.getOwnerId());
        authQuery.setAuthType("08");
        authQuery.setChannel(ThirdChannelEnum.T3_TAXI.getCode());
        if(!appThirdAuthService.checkAuth(authQuery)){
            return JSONUtil.toJsonStr(Result.error(ShTrafficEnum.FAILED.getCode(),"用户未授权"));
        }
        t3CarTravelVo.setOwnerId(customerLogin.getOwnerId());
        t3CarTravelVo.setUserNo(customerLogin.getUserNo());
        rabbitMqService.send(MQConstant.T3_USER_TRAVEL_PUSH, JSONUtil.toJsonStr(t3CarTravelVo));
        return JSONUtil.toJsonStr(Result.error(ShTrafficEnum.SUCCESS.getCode(), ShTrafficEnum.SUCCESS.getDesc()));
    }
}
