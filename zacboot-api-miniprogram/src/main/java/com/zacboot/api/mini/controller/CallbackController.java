package com.zacboot.api.mini.controller;

import com.zacboot.api.mini.beans.Constants;
import com.zacboot.api.mini.factory.ApiServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/callBack")
@RestController
public class CallbackController {

    @Autowired
    ApiServiceFactory apiServiceFactory;

    @GetMapping("/pdd/getCode")
    public void getPddCode(@RequestParam("code") String code){
        log.info("/pdd/getCode -->  {}",code);
        try {
            apiServiceFactory.getService(Constants.PLATFORM_PDD).getToken(code);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
