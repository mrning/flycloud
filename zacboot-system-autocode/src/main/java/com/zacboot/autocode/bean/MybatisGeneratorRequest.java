package com.zacboot.autocode.bean;

import com.zacboot.common.base.enums.PlatformEnum;
import lombok.Data;

@Data
public class MybatisGeneratorRequest {
    String schema;
    String tableName;
    String desc;
    PlatformEnum platform;
}
