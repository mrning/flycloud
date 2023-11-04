package com.zac.admin.beans.vos.request;

import com.zac.base.basebeans.PageRequest;
import com.zac.base.bizentity.SysDict;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class SysDictPageRequest extends PageRequest {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 父级名称
     */
    private String parentName;

    public SysDict converToDo(){
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(this,sysDict);
        return sysDict;
    }
}
