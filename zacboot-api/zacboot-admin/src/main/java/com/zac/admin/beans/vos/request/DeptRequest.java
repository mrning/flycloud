package com.zac.admin.beans.vos.request;

import cn.hutool.db.Page;
import com.zac.base.basebeans.PageRequest;
import lombok.Data;

@Data
public class DeptRequest extends PageRequest {

    /**
     * 机构/部门名称
     */
    private String departName;
}
