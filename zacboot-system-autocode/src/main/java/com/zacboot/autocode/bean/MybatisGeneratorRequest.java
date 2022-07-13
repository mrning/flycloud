package com.zacboot.autocode.bean;

import com.zacboot.common.base.enums.PlatformEnum;
import lombok.Data;

@Data
public class MybatisGeneratorRequest {
    String dataBaseName;
    String tableName;
    String desc;
    PlatformEnum platform;
}
