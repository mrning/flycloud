package com.zacboot.autocode.controller;

import com.zacboot.autocode.bean.MybatisGeneratorRequest;
import com.zacboot.autocode.service.MybatisGeneratorService;
import com.zacboot.autocode.service.SysMysqlCreateTableService;
import com.zacboot.common.base.enums.PlatformEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Mybatis自动生成代码")
@RestController
@RequestMapping("/api-autocode/mybatisGenerator")
@Slf4j
public class MybatisGeneratorController {

    @Autowired
    private MybatisGeneratorService mybatisGeneratorService;

    @Autowired
    private SysMysqlCreateTableService sysMysqlCreateTableService;

    @PostMapping(value = "/tableToCode")
    @ApiOperation("根据数据库字段生成代码")
    @ApiImplicitParam(name = "platform", value = "接口平台", dataTypeClass = PlatformEnum.class)
    public String tableToCode(@RequestBody MybatisGeneratorRequest mybatisGeneratorRequest) {
        return mybatisGeneratorService.doDenerator(mybatisGeneratorRequest);
    }

    @PostMapping(value = "/codeToTable")
    @ApiOperation("根据实体类创建或更新表结构")
    public void codeToTable(@RequestBody MybatisGeneratorRequest mybatisGeneratorRequest) {
        log.info("开始自动检查是否需要创建表 || 更新表");
        sysMysqlCreateTableService.createMysqlTable(mybatisGeneratorRequest);
    }
}

