package com.zac.pay.controller;

import cn.hutool.json.JSONObject;
import com.zac.base.basebeans.Result;
import com.zac.pay.service.BocPayService;
import com.zac.request.req.pay.BocPayUpRequest;
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
    public Result<String> payUp(@RequestBody BocPayUpRequest bocPayUpRequest) {
        JSONObject json = bocPayService.payUp(bocPayUpRequest);

        cn.hutool.json.JSONObject headJson = json.getJSONObject("head");
        if (!"ok".equals(headJson.getStr("responseCode"))) {
            return Result.error(headJson.getStr("responseInfo"), "请求异常");
        }
        return Result.success(json.getJSONObject("body").toStringPretty(), "操作成功");
    }
}
