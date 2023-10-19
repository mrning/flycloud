package com.lqjk.autocode.bean;

import com.lqjk.base.enums.UserClientEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "自动代码生成请求参数")
public class MybatisGeneratorRequest {

    @Schema(description = "库名，建表或者代码生成时连接的库名", example = "htx-admin")
    String schema;

    @Schema(description = "表名", example = "sys_user")
    String tableName;

    @Schema(description = "表名描述")
    String desc;

    @Schema(description = "平台类型，会影响到生成java类的路径")
    UserClientEnum platform;

    @Schema(description = "根据code建表时，自动扫描entity的包路径", example = "com.lqjk.base.bizentity")
    private String packagePath;

    @Schema(description = "根据code建表时，建表模式`update`表示更新，`create`表示删除原表重新创建")
    private String tableAuto;
}
