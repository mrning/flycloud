package com.lqjk.third.service.impl;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.lqjk.base.bizentity.SysUser;
import com.lqjk.base.utils.DateUtil;
import com.lqjk.base.utils.MD5Util;
import com.lqjk.base.utils.RedisUtil;
import com.lqjk.mq.constants.MQConstant;
import com.lqjk.mq.service.RabbitMqService;
import com.lqjk.third.beans.*;
import com.lqjk.third.mapper.CarbonAssetsUserInfoChannelMapper;
import com.lqjk.third.mapper.CarbonAssetsUserInfoMapper;
import com.lqjk.third.mapper.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CustomerService {
    private static final int randomSeqLength = 15;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CarbonAssetsUserInfoMapper carbonAssetsUserInfoMapper;
    @Autowired
    private CarbonAssetsUserInfoChannelMapper carbonAssetsUserInfoChannelMapper;
    @Autowired
    private MessagePushedService messagePushedService;
    @Autowired
    private RabbitMqService rabbitMqService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RedisUtil redisUtil;

    public UserInformation checkCustomerThirdParty(String phone, String nickname, String appChannel, String thirdChannel) {
        return customerService.generateCustomer(phone, nickname, appChannel, thirdChannel);
    }


    /**
     * 根据手机号查询用户信息
     *
     * @param phone
     * @return
     */
    public CustomerLogin getCustomerInfoByPhone(String phone) {
        return customerMapper.getCustomerInfoByPhone(phone);
    }

    /**
     * 根据ownerId查询用户信息
     *
     * @param ownerId
     * @return
     */
    public UserInformation selectUserInformationByOwnerId(Long ownerId) {
        return customerMapper.selectUserInformationByOwnerId(ownerId);
    }

    /**
     * @return void
     * @Author
     * @Description 注册
     * @Date 9:13 2020/4/22
     * @Param [phone]
     **/
    @Transactional(rollbackFor = Exception.class)
    public UserInformation generateCustomer(String phone, String nickname, String appChannel, String thirdChannel) {
        log.info("开始注册");
        SysUser user = new SysUser();
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        customerMapper.insertUser(user);

        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(phone);
        userLogin.setPhoneNumber(phone);
        userLogin.setCreateTime(LocalDateTime.now());
        //注册城市
        userLogin.setCity("2660");
        userLogin.setRandomSeq(RandomUtil.randomNumbers(randomSeqLength) + redisUtil.queryIndexCode());
        userLogin.setPhoneLogin(true);
        userLogin.setPasswordLogin(false);
        userLogin.setAppChannel(appChannel);
        customerMapper.insertCustomer(userLogin);
        log.info("已插入新用户");
        /**
         *插入用户详细信息
         */
        UserInformation userInformation = new UserInformation();
        userInformation.setSysUserLoginId(userLogin.getId());
        userInformation.setMobilePhone(phone);
        // 默认昵称
        if (StringUtils.isEmpty(nickname)) {
            userInformation.setNickName("践行者");
        } else {
            userInformation.setNickName(nickname);
        }
        String userNo = MD5Util.MD5Encode(phone, "utf-8");
        userInformation.setUserNo(userNo);
        userInformation.setAppChannel(appChannel);
        customerMapper.insertUserInformation(userInformation);
        //插入碳资产表
        CarbonAssetsUserInfoChannel carbonAssetsUserInfo = new CarbonAssetsUserInfoChannel();
        carbonAssetsUserInfo.setOwnerId(user.getId());
        carbonAssetsUserInfo.setBalanceCarbonAssetsGram(BigDecimal.ZERO);
        carbonAssetsUserInfo.setCarbonAssetsTypeCode(EnergyType.CARBON.getCode());
        carbonAssetsUserInfo.setTotalCarbonAssetsAmt(0);
        carbonAssetsUserInfo.setCreateDate(DateUtil.getDate());
        carbonAssetsUserInfo.setLastUpdateDate(DateUtil.getDate());
        carbonAssetsUserInfo.setAppChannel(appChannel);
        carbonAssetsUserInfo.setUserNo(userNo);
        carbonAssetsUserInfoMapper.insert(carbonAssetsUserInfo);
        carbonAssetsUserInfo.setThirdChannel(thirdChannel);
        carbonAssetsUserInfo.setAssetsId(carbonAssetsUserInfo.getId());
        carbonAssetsUserInfoChannelMapper.insert(carbonAssetsUserInfo);

        CarbonAssetsUserInfoChannel _carbonAssetsUserInfo = new CarbonAssetsUserInfoChannel();
        _carbonAssetsUserInfo.setOwnerId(user.getId());
        _carbonAssetsUserInfo.setBalanceCarbonAssetsGram(BigDecimal.ZERO);
        _carbonAssetsUserInfo.setCarbonAssetsTypeCode(EnergyType.CALORIE.getCode());
        _carbonAssetsUserInfo.setTotalCarbonAssetsAmt(0);
        _carbonAssetsUserInfo.setCreateDate(DateUtil.getDate());
        _carbonAssetsUserInfo.setLastUpdateDate(DateUtil.getDate());
        _carbonAssetsUserInfo.setAppChannel(appChannel);
        _carbonAssetsUserInfo.setUserNo(userNo);
        carbonAssetsUserInfoMapper.insert(_carbonAssetsUserInfo);
        _carbonAssetsUserInfo.setThirdChannel(thirdChannel);
        _carbonAssetsUserInfo.setAssetsId(_carbonAssetsUserInfo.getId());
        carbonAssetsUserInfoChannelMapper.insert(_carbonAssetsUserInfo);
        try {
            MessageParameter.Builder builder = MessageParameter.newBuilder();
            builder.setTitle("注册成功")
                    .setContent("终于等到你！欢迎您成为至臻生活塑造者的一员！请开启您的至臻体验吧~")
                    .setOwnerId(user.getId())
                    .setType(MessageParameter.REGISTER)
                    .setGeneralType("01")
                    .setJumpType("1")
                    .setJumpParam(null);

            MessageParameter messageParameter = builder.build();
            messageParameter.setUserNo(userNo);
            messageParameter.setThirdChannel(thirdChannel);
            messagePushedService.sendMessage(messageParameter);
        } catch (Exception e) {
            log.error("消息发送异常", e);
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", String.valueOf(user.getId()));
        log.info("信息发送到区块链");
        rabbitMqService.send(MQConstant.CREATE_AUTH_USER_HTX, JSONUtil.toJsonStr(paramMap));
        return userInformation;
    }

}
