package com.lqjk.autocode.controller;

import com.lqjk.autocode.bean.MybatisGeneratorRequest;
import com.lqjk.autocode.service.MybatisGeneratorService;
import com.lqjk.autocode.service.SysMysqlCreateTableService;
import com.lqjk.base.enums.PlatformEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mybatis自动生成代码")
@RestController
@RequestMapping("/api-autocode/mybatisGenerator")
@Slf4j
public class MybatisGeneratorController {

    @Autowired
    private MybatisGeneratorService mybatisGeneratorService;

    @Autowired
    private SysMysqlCreateTableService sysMysqlCreateTableService;

    @Operation(summary = "根据表结构生成代码")
    @PostMapping(value = "/tableToCode")
    public String tableToCode(@RequestBody MybatisGeneratorRequest mybatisGeneratorRequest) {
        return mybatisGeneratorService.doDenerator(mybatisGeneratorRequest);
    }

    @Operation(summary = "根据实体类生成表结构", description = "建表的话只需要输入 packagePath 参数即可")
    @PostMapping(value = "/codeToTable")
    public void codeToTable(@RequestBody MybatisGeneratorRequest mybatisGeneratorRequest) {
        log.info("开始自动检查是否需要创建表 || 更新表");
        sysMysqlCreateTableService.createMysqlTable(mybatisGeneratorRequest);
    }
}

