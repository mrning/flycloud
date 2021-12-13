package com.zac.flycloud.autocode.controller;

import com.zac.flycloud.autocode.service.MybatisGeneratorService;
import com.zac.flycloud.bean.enums.PlatformEnum;
import com.zac.flycloud.dao.actable.service.SysMysqlCreateTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private SysMysqlCreateTableService sysMysqlCreateTableService;

    @PostMapping(value = "/tableToCode")
    @ApiOperation("根据数据库字段生成表结构，代码生成入口")
    @ApiImplicitParam(name = "platform", value = "接口平台", dataTypeClass = PlatformEnum.class)
    public String tableToCode(String tableName, String desc, PlatformEnum platform) {
        return mybatisGeneratorService.doDenerator(tableName, desc, platform.getValue());
    }
    @PostMapping(value = "/codeToTable")
    @ApiOperation("根据实体类更新表结构，代码生成入口")
    @ApiImplicitParam(name = "platform", value = "接口平台", dataTypeClass = PlatformEnum.class)
    public void codeToTable() {
        log.info("开始自动检查是否需要创建表 || 更新表");
        sysMysqlCreateTableService.createMysqlTable();
    }
}

