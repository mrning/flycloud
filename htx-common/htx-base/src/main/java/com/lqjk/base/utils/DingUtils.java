package com.lqjk.base.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class DingUtils {

    public static String getSignUrl() {
        try {
            Long timestamp = System.currentTimeMillis();
            String secret = "SEC89028155269ce7838676dc19c0cdd4b7d47974de617f23286df291ecf9528fe0";
            String webHook = "https://oapi.dingtalk.com/robot/send?access_token=6898637ec5feae16ebc95b86ffadca9ac83baca1c4649b009246554ed8d52bdd";

            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            String sign = URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), StandardCharsets.UTF_8);

            return String.format(webHook + "&timestamp=%s&sign=%s", timestamp, sign);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendLinkMsg(String title, String text, String url) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(getSignUrl());
            OapiRobotSendRequest req = new OapiRobotSendRequest();
            req.setMsgtype("link");
            OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
            link.setTitle(title);
            link.setText(text);
            link.setMessageUrl(url);
            req.setLink(link);
            OapiRobotSendResponse rsp = client.execute(req);
            log.info("DingUtils#sendLinkMsg res = {}", rsp.getBody());
        } catch (Exception e) {
            log.error("DingUtils#sendLinkMsg error", e);
        }
    }
}
