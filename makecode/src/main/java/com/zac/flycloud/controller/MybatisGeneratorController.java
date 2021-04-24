package com.zac.flycloud.controller;

import com.zac.flycloud.enums.PlatformEnum;
import com.zac.flycloud.service.MybatisGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Mybatis自动生成代码")
@RestController
@RequestMapping("/api/mybatisGenerator")
@Slf4j
public class MybatisGeneratorController {

    @Autowired
    private MybatisGeneratorService mybatisGeneratorService;

    @RequestMapping(value = "/doGenerator", method = RequestMethod.POST)
    @ApiOperation("代码生成入口")
    @ApiImplicitParam(name = "platform", value = "接口平台", dataTypeClass = PlatformEnum.class)
    public String doDenerator(String tableName, String desc, PlatformEnum platform) {
        return mybatisGeneratorService.doDenerator(tableName, desc, platform.getValue());
    }
}

