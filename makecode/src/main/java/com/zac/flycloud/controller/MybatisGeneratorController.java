package com.zac.flycloud.controller;

import com.zac.flycloud.service.MybatisGeneratorService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public String doDenerator() {
        return mybatisGeneratorService.doDenerator();
    }
}

