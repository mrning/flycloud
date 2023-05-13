package com.zacboot.autocode.bean;

import com.zacboot.common.base.enums.PlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MybatisGeneratorRequest {

    @ApiModelProperty(value = "库名，建表或者代码生成时连接的库名",example = "zacboot")
    String schema;

    @ApiModelProperty(value = "表名",example = "sys_user")
    String tableName;

    @ApiModelProperty(value = "表名描述",example = "用戶管理")
    String desc;

    @ApiModelProperty("平台类型，会影响到生成java类的路径")
    PlatformEnum platform;

    @ApiModelProperty(value = "根据code建表时，自动扫描entity的包路径",example = "com.zacboot.system.core.entity.admin")
    private String packagePath;

    @ApiModelProperty(value = "根据code建表时，建表模式 update表示更新，create表示删除原表重新创建", example = "create")
    private String tableAuto;
}
