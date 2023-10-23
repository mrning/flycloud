package com.lqjk.third.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lqjk.base.utils.RedisUtil;
import com.lqjk.mq.constants.MQConstant;
import com.lqjk.mq.service.RabbitMqService;
import com.lqjk.third.beans.*;
import com.lqjk.third.mapper.AppThirdAuthMapper;
import com.lqjk.third.mapper.CarbonAssetsUserInfoChannelMapper;
import com.lqjk.third.mapper.CarbonAssetsUserInfoMapper;
import com.lqjk.third.mapper.CustomerTokenThirdMapper;
import com.lqjk.third.service.*;
import com.lqjk.third.utils.Sm3Util;
import com.lqjk.third.utils.Sm4Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author YJD
 * @Date 2023/4/6 15:42
 * @Version 1.0
 */
@Slf4j
@Service
public class ShTrafficServiceImpl implements ShTrafficService {

    @Autowired
    private AppThirdChannelService appThirdChannelService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerTokenThirdMapper customerTokenThirdMapper;
    @Autowired
    private RabbitMqService rabbitMqService;

    @Autowired
    private AppThirdAuthMapper appThirdAuthMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CustomerTokenThirdService customerTokenThirdService;

    @Autowired
    private MetroTakeRecordService metroTakeRecordService;
    @Autowired
    private BusTakeRecordService busTakeRecordService;
    @Autowired
    private CarbonAssetsUserInfoMapper carbonAssetsUserInfoMapper;

    @Autowired
    private CarbonAssetsUserInfoChannelMapper carbonAssetsUserInfoChannelMapper;

    @Value("${shTraffic.sm3Key}")
    private String sm3Key;
    @Value("${shTraffic.sm4Key}")
    private String sm4Key;

    @Value("${shTraffic.url}")
    private String shTrafficUrl;

    @Override
    public String authorization(AuthUserVo authUser, List<String> cardNoList) {
        log.info("调用授权{}", JSONUtil.toJsonStr(authUser));
        try {
            AppThirdChannel appThirdChannel = appThirdChannelService.selectByChannel(ThirdChannelEnum.SHANGHAI_APP.getCode());
            if (null == appThirdChannel) {
                log.info("该channel不存在或未启用");
                return null;
            }
            CustomerLogin customerLogin = customerService.getCustomerInfoByPhone(authUser.getMobile());
            if (null == customerLogin) {
                log.info("新用户授权沪碳行phone={}", authUser.getMobile());
                UserInformation userInformation = customerService.checkCustomerThirdParty(authUser.getMobile(), "", ThirdChannelEnum.HTX_APP.getCode(), ThirdChannelEnum.SHANGHAI_APP.getCode());
                if (userInformation == null) {
                    log.warn("授权失败phone={}", authUser.getMobile());
                    return null;
                }
                customerLogin = new CustomerLogin();
                customerLogin.setOwnerId(userInformation.getId());
                customerLogin.setPhoneNumber(userInformation.getMobilePhone());
                customerLogin.setAppChannel(userInformation.getAppChannel());
                customerLogin.setUserNo(userInformation.getUserNo());
            } else {
                CarbonAssetsUserInfoChannel query = new CarbonAssetsUserInfoChannel();
                query.setOwnerId(customerLogin.getOwnerId());
                query.setThirdChannel(ThirdChannelEnum.SHANGHAI_APP.getCode());
                query.setAppChannel(ThirdChannelEnum.HTX_APP.getCode());
                List<CarbonAssetsUserInfoChannel> cnannelList = carbonAssetsUserInfoChannelMapper.generalQuery(query);
                if (CollectionUtil.isEmpty(cnannelList)) {
                    List<CarbonAssetsUserInfoChannel> list = carbonAssetsUserInfoMapper.generalQuery(query);
                    for (CarbonAssetsUserInfoChannel carbonAssetsUserInfoChannel : list) {
                        carbonAssetsUserInfoChannel.setAssetsId(carbonAssetsUserInfoChannel.getId());
                        carbonAssetsUserInfoChannel.setThirdChannel(ThirdChannelEnum.SHANGHAI_APP.getCode());
                        carbonAssetsUserInfoChannelMapper.insert(carbonAssetsUserInfoChannel);
                    }
                }
                log.info("老用户授权沪碳行phone={}", authUser.getMobile());
            }

            CustomerTokenThird customerTokenThird = customerTokenThirdService.selectByChannelAndToken(ThirdChannelEnum.SHANGHAI_APP.getCode(), authUser.getUserToken());
            if (null != customerTokenThird) {
                log.info("授权token不为空token={}", authUser.getUserToken());
                //不为空的情况下，检查当前用户的ownerId  是否等于存在的ownerId
                if (!customerLogin.getOwnerId().equals(customerTokenThird.getOwnerId())) {
                    log.warn("授权失败该token已有用户使用token={},baseRequest={}", authUser.getUserToken(), JSONUtil.toJsonStr(authUser));
                    return "授权失败";
                }
            } else {
                log.info("上海交通卡APP用户授权,保存来自上海交通卡的token={}", authUser.getUserToken());
                CustomerTokenThird entity = new CustomerTokenThird();
                entity.setToken(authUser.getUserToken());
                entity.setChannel(ThirdChannelEnum.SHANGHAI_APP.getCode());
                entity.setCustomerEquipment(ThirdChannelEnum.SHANGHAI_APP.getCode());
                entity.setDisabled(false);
                entity.setCreateDate(new Date());
                entity.setUpdateDate(new Date());
                entity.setOwnerId(customerLogin.getOwnerId());
                customerTokenThirdMapper.insert(entity);
            }
            customerLogin.setThirdChannel(ThirdChannelEnum.SHANGHAI_APP.getCode());
            customerLogin.setCardNoList(cardNoList);
            //发送保存授权地铁用户信息消息
            rabbitMqService.send(MQConstant.CARBON_THIRD_SAVE_AUTH, JSONUtil.toJsonStr(customerLogin));
            return JSONUtil.toJsonStr(customerLogin);
        } catch (Exception e) {
            log.error("上海交通卡H5授权异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String cancelAuth(AuthUserVo authUser) {
        CustomerTokenThird customerTokenThird = customerTokenThirdService.selectByChannelAndToken(ThirdChannelEnum.SHANGHAI_APP.getCode(), authUser.getUserToken());
        if (customerTokenThird == null) {
            return ShTrafficEnum.NOT_FIND.getDesc() + ":" + authUser.getUserToken();
        }
        AppThirdAuth authQuery = new AppThirdAuth();
        authQuery.setChannel(ThirdChannelEnum.SHANGHAI_APP.getCode())
                .setOwnerId(customerTokenThird.getOwnerId());
        List<AppThirdAuth> authList = appThirdAuthMapper.selectByAppThirdAuth(authQuery);
        if (CollUtil.isNotEmpty(authList)) {
            Long thirdAuthId = null;
            for (AppThirdAuth authSaved : authList) {
                thirdAuthId = authSaved.getId();
                AppThirdAuth thirdAuth = new AppThirdAuth();
                thirdAuth.setId(authSaved.getId());
                thirdAuth.setStatus("0");
                thirdAuth.setUpdateTime(DateUtil.date());
                appThirdAuthMapper.update(thirdAuth);
            }
            Set<String> keys = redisUtil.keys(String.format("qtx:authExt::%s*", authUser.getUserToken()));
            if (CollUtil.isNotEmpty(keys)) {
                redisUtil.del(Arrays.toString(keys.toArray()));
            }
            customerTokenThirdService.deleteCustomerToken(ThirdChannelEnum.SHANGHAI_APP.getCode(), authUser.getUserToken());
            if (thirdAuthId != null) {
                appThirdAuthMapper.deleteCardNo(authList.get(0).getOwnerId(), thirdAuthId);
            }
        }
        log.info("解除授权:{}", authUser.getUserToken());
        return ShTrafficEnum.SUCCESS.getDesc();
    }

    @Override
    public String authModify(List<AuthUserVo> userList) {
        AuthUserVo authUser = userList.get(0);
        CustomerTokenThird customerTokenThird = customerTokenThirdService.selectByChannelAndToken(ThirdChannelEnum.SHANGHAI_APP.getCode(), authUser.getUserToken());
        UserInformation userInformation = customerService.selectUserInformationByOwnerId(customerTokenThird.getOwnerId());
        AppThirdAuth authQuery = new AppThirdAuth();
        authQuery.setChannel(ThirdChannelEnum.SHANGHAI_APP.getCode())
                .setAuthType("05")
                .setOwnerId(customerTokenThird.getOwnerId());
        List<AppThirdAuth> authList = appThirdAuthMapper.selectByAppThirdAuth(authQuery);
        AppThirdAuth appThirdAuth = authList.get(0);
        List<String> addCardNoList = new ArrayList<String>();
        List<String> cancelCardNoList = new ArrayList<String>();
        for (AuthUserVo user : userList) {
            if (AuthTransType.AUTH.equals(user.getType())) {
                addCardNoList.add(user.getCardNo());
            } else if (AuthTransType.AUTH_CANCEL.equals(user.getType())) {
                cancelCardNoList.add(user.getCardNo());
            }
        }
        if (!addCardNoList.isEmpty()) {
            appThirdAuthMapper.insertUserAuthCardNo(customerTokenThird.getOwnerId(), addCardNoList, userInformation.getUserNo());
        }
        if (!cancelCardNoList.isEmpty()) {
            appThirdAuthMapper.cancelUserAuthCardNo(customerTokenThird.getOwnerId(), appThirdAuth.getId(), cancelCardNoList);
        }
        return ShTrafficEnum.SUCCESS.getDesc();
    }

    @Override
    public void uploadMetroInfo(ShTradeInfoVo shTradeInfoVo, UserAuthCardNo userAuthCardNo) {
        try {
            MetroTakeRecord metroTakeRecord = new MetroTakeRecord();
            metroTakeRecord.setTransOrderNo(shTradeInfoVo.getTravelNo());
            metroTakeRecord.setAppChannel(ThirdChannelEnum.HTX_APP.getCode());
            metroTakeRecord.setUserId(userAuthCardNo.getOwnerId());
            metroTakeRecord.setUserNo(userAuthCardNo.getUserNo());
            metroTakeRecord.setThirdChannel(ThirdChannelEnum.SHANGHAI_APP.getCode());
            metroTakeRecord.setSource(ThirdChannelEnum.SHANGHAI_APP.getCode());
            metroTakeRecord.setEntryDate(DateUtil.parse(shTradeInfoVo.getIntoTransTime(), "yyyyMMddHHmmss"));
            metroTakeRecord.setExitDate(DateUtil.parse(shTradeInfoVo.getOutTransTime(), "yyyyMMddHHmmss"));
            metroTakeRecord.setTransMileage(new BigDecimal(shTradeInfoVo.getMileage()));
            metroTakeRecordService.uploadMetroInfo(metroTakeRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("对上海交通卡地铁行程处理异常ownerId={}", userAuthCardNo.getOwnerId(), e);
        }
    }

    @Override
    public void uploadBusInfo(ShTradeInfoVo shTradeInfoVo, UserAuthCardNo userAuthCardNo) {
        try {
            BusTakeRecord busTakeRecord = new BusTakeRecord();
            busTakeRecord.setTransOrderNo(shTradeInfoVo.getTravelNo());
            busTakeRecord.setAppChannel(ThirdChannelEnum.HTX_APP.getCode());
            busTakeRecord.setUserId(userAuthCardNo.getOwnerId());
            busTakeRecord.setUserNo(userAuthCardNo.getUserNo());
            busTakeRecord.setThirdChannel(ThirdChannelEnum.SHANGHAI_APP.getCode());
            busTakeRecord.setSource(ThirdChannelEnum.SHANGHAI_APP.getCode());
            busTakeRecord.setTransDate(shTradeInfoVo.getIntoTransTime().substring(0, 8));
            busTakeRecord.setTransTime(shTradeInfoVo.getIntoTransTime().substring(8));
            busTakeRecordService.uploadBusInfo(busTakeRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("对上海交通卡公交行程处理异常ownerId={}", userAuthCardNo.getOwnerId(), e);
        }
    }

    @Override
    public String changePhone(String userToken, String mobile) {
        log.info("查询token用户");
        CustomerTokenThird customerTokenThird = customerTokenThirdService.selectByChannelAndToken(ThirdChannelEnum.SHANGHAI_APP.getCode(), userToken);
        AppThirdAuth authQuery = new AppThirdAuth();
        authQuery.setChannel(ThirdChannelEnum.SHANGHAI_APP.getCode())
                .setAuthType("05")
                .setOwnerId(customerTokenThird.getOwnerId());
        List<AppThirdAuth> authList = appThirdAuthMapper.selectByAppThirdAuth(authQuery);
        AppThirdAuth appThirdAuth = authList.get(0);
        //查询原手机号下的卡号
        List<String> cardNoList = appThirdAuthMapper.getCardNoList(customerTokenThird.getOwnerId(), appThirdAuth.getId());
        //解除源token授权
        AuthUserVo canceUser = new AuthUserVo();
        canceUser.setUserToken(userToken);
        this.cancelAuth(canceUser);
        AuthUserVo authorizationUser = new AuthUserVo();
        authorizationUser.setUserToken(userToken);
        authorizationUser.setMobile(mobile);
        //授权新用户手机号
        return this.authorization(authorizationUser, cardNoList);
    }

    @Override
    public UserAuthCardNo getOwnerIdByToken(String token) {
        CustomerTokenThird customerTokenThird = customerTokenThirdService.selectByChannelAndToken(ThirdChannelEnum.SHANGHAI_APP.getCode(), token);
        UserAuthCardNo userAuthCardNo = null;
        log.info("查询本地三方用户：{}", JSONUtil.toJsonStr(customerTokenThird));
        if (customerTokenThird == null) {
            Map<String, String> param = new HashMap<String, String>();
            param.put("reqId", UUID.randomUUID().toString().replace("-", ""));
            param.put("resourceId", "NFC_TRAVE");
            param.put("userToken", token);
            String response = sendUrl(shTrafficUrl + "/marketing-gate/auth/getAuthInfo", encryptParam(param));
            JSONObject authInfo = JSONUtil.parseObj(response);
            String mobile = authInfo.getStr("mobile");
            if (StringUtils.isBlank(mobile)) {
                log.info("手机号返回为空");
                return null;
            }
            AuthUserVo authUser = new AuthUserVo();
            authUser.setMobile(mobile);
            authUser.setUserToken(token);
            try {
                String baseVo = authorization(authUser, null);
                CustomerLogin customerLogin = (CustomerLogin) com.alibaba.fastjson.JSONObject.parse(baseVo);
                userAuthCardNo = new UserAuthCardNo();
                userAuthCardNo.setOwnerId(customerLogin.getOwnerId());
                userAuthCardNo.setUserNo(customerLogin.getUserNo());
            } catch (Exception e) {
                log.error("上海交通卡推送异常：{}", e);
                return null;
            }
        } else {
            userAuthCardNo = new UserAuthCardNo();
            userAuthCardNo.setOwnerId(customerTokenThird.getOwnerId());
        }
        return userAuthCardNo;
    }

    public String sendUrl(String url, String paeam) {
        log.info("调用上海交通卡APP发送参数{}", paeam);
        String response = HttpUtil.post(url, paeam);
        log.info("调用上海交通卡APP返回参数{}", response);
        String rs = getDecryptBody(response);
        log.info("{}:地址解密后数据{}", url, rs);
        return rs;
    }

    public String encryptParam(Map<String, String> param) {
        log.info("请求加密参数：{}", JSONUtil.toJsonStr(param));
        JSONObject jsonObject = new JSONObject();
        try {
            String body = Sm4Util.encryptEcb(sm4Key, JSONUtil.toJsonStr(param));
            String sign = Sm3Util.encrypt(body + sm3Key);
            jsonObject.set("keyVersion", "1.0");
            jsonObject.set("orgCode", "icago");
            jsonObject.set("subOrgCode", "icago");
            jsonObject.set("body", body);
            jsonObject.set("sign", sign);
        } catch (Exception e) {
            log.error("sm4加密异常", e);
        }
        return jsonObject.toString();
    }

    /**
     * 解密操作
     *
     * @param jsonStr json字符串
     * @return 明文json
     */
    private String getDecryptBody(String jsonStr) {
        JSONObject json = JSONUtil.parseObj(jsonStr);
        String body = json.getStr("body");
        String sign = json.getStr("sign");
        try {
            if (!Sm3Util.verify(body + sm3Key, sign)) {
                return null;
            }
            return Sm4Util.decryptEcb(sm4Key, body);
        } catch (Exception e) {
            log.error("验签失败");
            return null;
        }
    }
}
