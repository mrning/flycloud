package com.zacboot.admin.beans.vos.request;

import com.zacboot.system.core.entity.admin.SysDict;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class SysDictPageRequest {

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

    private Long page;

    private Long limit;

    public SysDict converToDo(){
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(this,sysDict);
        return sysDict;
    }
}
