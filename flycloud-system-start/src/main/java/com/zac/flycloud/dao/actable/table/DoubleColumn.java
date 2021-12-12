package com.zac.flycloud.dao.actable.table;

import lombok.Data;

/**
 * 浮点型
 *
 **/
@Data
public class DoubleColumn extends CommonColumn {

    public DoubleColumn() {
        this.setType("decimal");
        this.setLength(20);
        this.setDecimalLength(2);
    }


}
