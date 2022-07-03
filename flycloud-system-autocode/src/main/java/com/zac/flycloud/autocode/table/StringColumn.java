package com.zac.flycloud.autocode.table;

import lombok.Data;

/**
 * 字符串
 *
 **/
@Data
public class StringColumn extends CommonColumn {


    public StringColumn() {
        this.setType("varchar");
        this.setLength(200);
    }

    public Class getJavaType() {
        return String.class;
    }
}
