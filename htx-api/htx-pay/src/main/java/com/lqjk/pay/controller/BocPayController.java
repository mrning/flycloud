package com.lqjk.pay.controller;

import com.lqjk.base.basebeans.Result;
import com.lqjk.pay.service.BocPayService;
import com.lqjk.request.req.pay.BocPayUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "中行数币支付接口列表")
@RestController
@RequestMapping("/bocPay")
@RequiredArgsConstructor
public class BocPayController {

    private final BocPayService bocPayService;

    @Operation(summary = "拉起支付")
    @PostMapping("/up")
    public Result<String> payUp(@RequestBody BocPayUpRequest bocPayUpRequest){
        return Result.success(bocPayService.payUp(bocPayUpRequest), "操作成功");
    }
}
