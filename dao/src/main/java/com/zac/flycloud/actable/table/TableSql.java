package com.zac.flycloud.actable.table;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 表SQL
 *
 **/
@Data
public class TableSql {

    /**
     * 表名
     */
    private String name;
    /**
     * 主键
     */
    private String primaryKey;

    /**
     * 字段SQL
     */
    private List<ColumnSql> columnSqls = new ArrayList<ColumnSql>();

}
