package com.zac.flycloud.dao.actable.table;

import lombok.Data;

/**
 * 数字
 *
 **/
@Data
public class IntegerColumn extends CommonColumn {


    public IntegerColumn() {
        this.setType("int");
        this.setLength(12);
    }


}
