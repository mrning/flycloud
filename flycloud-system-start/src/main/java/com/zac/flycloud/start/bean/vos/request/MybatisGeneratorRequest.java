package com.zac.flycloud.start.bean.vos.request;

import com.zac.flycloud.start.bean.enums.PlatformEnum;
import lombok.Data;

@Data
public class MybatisGeneratorRequest {
    String dataBaseName;
    String tableName;
    String desc;
    PlatformEnum platform;
}
