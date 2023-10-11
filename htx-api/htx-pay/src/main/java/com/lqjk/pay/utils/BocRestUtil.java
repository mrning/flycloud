package com.lqjk.pay.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.json.XML;
import com.lqjk.base.utils.RestUtil;
import com.lqjk.pay.beans.BocRequestVo;
import com.lqjk.pay.constants.PayUrlConstant;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BocRestUtil {

    public static String sendBocRest(String bocMerchantNo,String serviceHost,String message,Long messageId){
        BocRequestVo bocRequestVo = new BocRequestVo();
        bocRequestVo.setMerchantNo(Base64.encode(bocMerchantNo));
        bocRequestVo.setMessageId(Base64.encode(messageId.toString()));
        bocRequestVo.setMessage(Base64.encode(XML.toXml(message)));

        Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA);
        bocRequestVo.setSignature(StringUtils.toEncodedString(sign.sign(XML.toXml(message)), StandardCharsets.UTF_8));

        return RestUtil.postFormXml(serviceHost + PayUrlConstant.PAY_UP,null, XML.toXml(bocRequestVo));
    }
}
