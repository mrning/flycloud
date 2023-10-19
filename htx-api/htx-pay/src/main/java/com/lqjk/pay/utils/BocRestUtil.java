package com.lqjk.pay.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.XML;
import com.alibaba.fastjson.JSONObject;
import com.lqjk.base.utils.RestUtil;
import com.lqjk.pay.beans.BocRequestVo;
import com.lqjk.pay.constants.PayUrlConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BocRestUtil {

    /**
     * 加签私钥路径
     */
    static final String signPrivateKeyPath = "privateKey/boc/95566SW010004857.pfx";
    /**
     * 验签公钥路径
     */
    final String verifyKeyPath = "privateKey/boc/boccfcaTest.cer";

    public static String sendBocRest(String bocMerchantNo, String serviceHost, String message, String messageId, String privatePwd) {
        BocRequestVo bocRequestVo = new BocRequestVo();
        bocRequestVo.setMerchantNo(Base64.encode(bocMerchantNo));
        bocRequestVo.setMessageId(Base64.encode(messageId));
        bocRequestVo.setMessage(Base64.encode(XML.toXml(message)));
        bocRequestVo.setSignature(getBocSign(privatePwd, XML.toXml(message)));

        return RestUtil.postFormXml(serviceHost + PayUrlConstant.PAY_UP,null, JSONObject.parseObject(JSONObject.toJSONString(bocRequestVo)));
    }

    @NotNull
    private static String getBocSign(String privatePwd, String content) {
        try {
            // 加载私钥文件
            File privateKeyFile = ResourceUtils.getFile("classpath:" + signPrivateKeyPath);
            // 这里是测试证书加签,根据生产上的环境切换成产的证书
            PKCS7Tool signer = PKCS7Tool.getSigner(privateKeyFile.getAbsolutePath(), privatePwd, privatePwd);
            //调用加签方法
            return signer.sign(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
