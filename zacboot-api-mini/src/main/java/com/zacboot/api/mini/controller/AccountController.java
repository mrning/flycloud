package com.zacboot.api.mini.controller;

import com.zacboot.api.mini.beans.reponses.PhoneLoginResponse;
import com.zacboot.api.mini.beans.requests.PhoneLoginRequest;
import com.zacboot.api.mini.service.AccountService;
import com.zacboot.common.base.basebeans.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("phoneLogin")
    public Result<PhoneLoginResponse> phoneLogin(@RequestBody PhoneLoginRequest request){
        return Result.success(accountService.phoneLogin(request));
    }
}
