package com.lqjk.pay.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.XML;
import com.alibaba.fastjson.JSONObject;
import com.lqjk.base.utils.DateUtil;
import com.lqjk.base.utils.UrlIPUtil;
import com.lqjk.pay.service.BocPayService;
import com.lqjk.pay.utils.BocRestUtil;
import com.lqjk.request.req.pay.BocPayUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Slf4j
@Service
public class BocPayServiceImpl implements BocPayService {
    /**
     * 中行分配的商户号
     */
    @Value("${htx.pay.boc.merchantNo:''}")
    private String bocMerchantNo;

    /**
     * 中行数币支付请求host
     */
    @Value("${htx.pay.boc.serviceHost:''}")
    private String serviceHost;

    /**
     * 中行数币支付私钥密码
     */
    @Value("${htx.pay.boc.privateKeyPwd:''}")
    private String privateKeyPwd;

    @Override
    public cn.hutool.json.JSONObject payUp(BocPayUpRequest bocPayUpRequest) {

        String dateTime = DateUtil.formatDateTimeYyyymmddhhmmss();
        JSONObject request = new JSONObject();
        JSONObject head = new JSONObject();
        head.put("requestTime", dateTime);
        JSONObject body = new JSONObject();
        //商户中行商户号
        body.put("merchantNo", bocMerchantNo);
        //终端设备号，商户自定义，如门店编号，只能为字母或数字
        body.put("deviceInfo", "htxapp");
        //随机字符串，不长于32位。推荐随机数生成算法，只能为字母或数字
        body.put("nonceStr", RandomUtil.randomString(16));
        //商品或支付单简要描述，只能为汉字、字母或数字
        body.put("productName", "沪碳行中行数币支付");
        //商品详情，只能为汉字、字母或数字
        body.put("detail", "沪碳行中行数币支付");
        //商户系统内部订单号，要求30个字符内，只能为字母或数字
        body.put("merOrderNo", bocPayUpRequest.getHtxOrderNo());
        //订单金额，整数位不要进行前补零,小数位补齐2位小数
        //即：不超过10位整数位+1位小数点+2位小数
        //无效格式如123，.10，1.1,有效格式如1.00，0.10
        body.put("totalAmount", "0.01");
        //币种，当前只支持人民币，编码为001
        body.put("currency", "001");
        //发起支付的机器IP，仅支持IPV4
        body.put("terminalIP", "127.0.0.1");
        //订单生成时间 格式：YYYYMMDDHHMISS 示例：20140226152159
        body.put("timeStart", dateTime);
        //来源渠道
        body.put("orderChannel", "0001");
        // 消费场景子类，具体上送请参考消费子场景说明
        // 消费=D204 其他类消费 = 02043
        body.put("tranCode", "D20402043");
        //交易类型 TT04：H5/APP拉起支付
        body.put("tradeTypeCode", "TT04");
        //交易地址 线下场景填写机具布放地址；线上场景填写网络交易平台网络地址
        body.put("tradePlace", "61.169.87.122");

        request.put("head", head);
        request.put("body", body);

        try {
            log.info("中行拉起支付接口，入参\n {}", request.toJSONString());
            String res = BocRestUtil.sendBocRest(bocMerchantNo, serviceHost, request.toJSONString(), "219701",privateKeyPwd);
            cn.hutool.json.JSONObject resJson = XML.toJSONObject(Base64.decodeStr(res));
            log.info("中行拉起支付接口，返回结果为\n {}", resJson);
            return resJson.getJSONObject("response");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
