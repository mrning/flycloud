package com.zac.flycloud.controller;

import com.zac.flycloud.enums.PlatformEnum;
import com.zac.flycloud.service.MybatisGeneratorService;
import io.swagger.annotations.*;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public String doDenerator(String tableName, PlatformEnum platform) {
        return mybatisGeneratorService.doDenerator(tableName, platform.getValue());
    }
}

