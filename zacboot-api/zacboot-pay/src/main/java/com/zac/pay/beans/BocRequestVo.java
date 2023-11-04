package com.zac.pay.beans;

import cn.hutool.core.codec.Base64;
import lombok.Data;

/**
 * 1.所有请求的参数值（除signature字段）都需要分别进行Base64编码后进行上送。
 * 2.使用浏览器发送Content-Type为application/x-www-form-urlencoded的信息时会对Base64编码后的数据自动进行URL编码。
 * Base64编码的请求数据发送后，银行后台会对数据做URL解码，若出现解码错误，需要商户检查是否对请求数据做了URL编码。
 */
@Data
public class BocRequestVo {
    /**
     * 商户中行商户号（最大20位数字）
     */
    private String merchantNo;

    private String version = Base64.encode("1.0.1");
    /**
     * 交易码
     */
    private String messageId;
    /**
     * 签名方法，固定上送：P7
     */
    private String security = Base64.encode("P7");
    /**
     * 请求报文明文信息: 将业务数据组成XML字符串格式进行UTF-8格式的转码
     */
    private String message;
    /**
     * 请求报文签名信息：对报文原文XML字符串格式的字节数组（UTF-8格式）进行签名
     */
    private String signature;
}
