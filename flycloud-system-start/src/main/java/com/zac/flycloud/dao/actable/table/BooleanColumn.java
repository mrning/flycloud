package com.zac.flycloud.dao.actable.table;

import lombok.Data;

/**
 * 布尔
 *
 * @author starmark
 * @create 2018-04-07 12:28
 **/
@Data
public class BooleanColumn extends CommonColumn {


    public BooleanColumn() {
        this.setType("bit");
        this.setLength(1);
    }


}
