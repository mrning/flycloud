package com.lqjk.autocode.table;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DecimalColumn extends CommonColumn{

    public DecimalColumn() {
        this.setType("decimal");
        this.setLength(2);
    }

    public Class getJavaType() {
        return BigDecimal.class;
    }
}
