package com.zacboot.autocode.bean;

import com.zacboot.common.base.enums.PlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MybatisGeneratorRequest {

    @ApiModelProperty("库名，建表或者代码生成时连接的库名")
    String schema;

    @ApiModelProperty("表名")
    String tableName;

    @ApiModelProperty("表名描述")
    String desc;

    @ApiModelProperty("平台类型，会影响到生成java类的路径")
    PlatformEnum platform;

    @ApiModelProperty("根据code建表时，自动扫描entity的包路径")
    private String packagePath;

    @ApiModelProperty("根据code建表时，建表模式 update表示更新，create表示删除原表重新创建")
    private String tableAuto;
}
