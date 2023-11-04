package com.zac.admin.beans.vos.request;

import cn.hutool.db.Page;
import com.zac.base.basebeans.PageRequest;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

@Data
public class UserDeptRequest extends PageRequest {

    @SchemaProperty(name = "部门uuid")
    private String deptUuid;
}
