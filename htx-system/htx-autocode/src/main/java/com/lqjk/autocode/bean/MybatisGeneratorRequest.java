package com.lqjk.autocode.bean;

import com.lqjk.base.enums.PlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MybatisGeneratorRequest {

    @SchemaProperty(name = "库名，建表或者代码生成时连接的库名",example = "htx")
    String schema;

    @SchemaProperty(name = "表名",example = "sys_user")
    String tableName;

    @SchemaProperty(name = "表名描述",example = "用戶管理")
    String desc;

    @ApiModelProperty("平台类型，会影响到生成java类的路径")
    PlatformEnum platform;

    @SchemaProperty(name = "根据code建表时，自动扫描entity的包路径",example = "com.lqjk.system.core.entity.admin")
    private String packagePath;

    @SchemaProperty(name = "根据code建表时，建表模式 update表示更新，create表示删除原表重新创建", example = "create")
    private String tableAuto;
}
