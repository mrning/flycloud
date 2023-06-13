package com.zacboot.api.mini.controller;

import com.zacboot.api.mini.beans.requests.WxMiniLoginRequest;
import com.zacboot.api.mini.service.WeChatService;
import com.zacboot.common.base.basebeans.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wx")
public class WeChatController {

    private final WeChatService weChatService;

    @PostMapping("/mini/login")
    private Result<String> miniLogin(@RequestBody WxMiniLoginRequest wxMiniLoginRequest) {
        String session = weChatService.codeToSession(wxMiniLoginRequest.getCode());
        return Result.success(session, "ok");
    }
}
