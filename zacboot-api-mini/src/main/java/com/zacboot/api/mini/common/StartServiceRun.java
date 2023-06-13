package com.zacboot.api.mini.common;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisBetterConfigImpl;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.common.redis.WxRedisOps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StartServiceRun implements CommandLineRunner {

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${wx.mini.appId}")
    private String appId;

    @Value("${wx.mini.appSecret}")
    private String appSecret;

    @Override
    public void run(String... args) throws Exception {
        WxRedisOps redisOps = new RedisTemplateWxRedisOps(stringRedisTemplate);
        WxMaRedisBetterConfigImpl wxMaRedisConfig = new WxMaRedisBetterConfigImpl(redisOps,"wst-mini");
        wxMaRedisConfig.setAppid(appId);
        wxMaRedisConfig.setSecret(appSecret);

        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaRedisConfig);
        SpringUtil.registerBean("wxMaService",wxMaService);
    }
}
