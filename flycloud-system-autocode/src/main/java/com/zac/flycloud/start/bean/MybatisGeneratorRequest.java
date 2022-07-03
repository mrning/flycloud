package com.zac.flycloud.start.bean;

import com.zac.flycloud.common.enums.PlatformEnum;
import lombok.Data;

@Data
public class MybatisGeneratorRequest {
    String dataBaseName;
    String tableName;
    String desc;
    PlatformEnum platform;
}
