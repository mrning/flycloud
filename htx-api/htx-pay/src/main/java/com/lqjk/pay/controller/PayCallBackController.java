package com.lqjk.pay.controller;

import com.lqjk.pay.beans.BocPayBackRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PayCallBackController {

    @PostMapping("/bocPayBack")
    public void bocPayBack(@RequestBody BocPayBackRequest bocPayBackRequest){
        log.info("中行支付回调入参为：{}",bocPayBackRequest);
    }
}
