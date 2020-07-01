package com.zac.flycloud.actable.table;

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
