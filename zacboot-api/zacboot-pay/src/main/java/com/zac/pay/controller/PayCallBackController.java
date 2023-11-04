package com.zac.pay.controller;

import com.zac.pay.beans.BocPayBackRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pay")
@Tag(name = "支付回调接口")
public class PayCallBackController {

    @PostMapping("/bocPayBack")
    @Operation(summary = "中行支付回调")
    public void bocPayBack(@RequestBody BocPayBackRequest bocPayBackRequest){
        log.info("中行支付回调入参为：{}",bocPayBackRequest);
    }
}
