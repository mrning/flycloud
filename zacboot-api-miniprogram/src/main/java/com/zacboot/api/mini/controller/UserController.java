package com.zacboot.api.mini.controller;

import com.zacboot.api.mini.beans.reponses.SaveUserInfoResponse;
import com.zacboot.api.mini.beans.requests.SaveUserInfoRequest;
import com.zacboot.api.mini.service.UserService;
import com.zacboot.common.base.basebeans.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块
 */
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/saveInfo")
    public Result<SaveUserInfoResponse> saveUserInfo(@RequestBody SaveUserInfoRequest request){
        return Result.success(userService.saveUserInfo(request));
    }
}
