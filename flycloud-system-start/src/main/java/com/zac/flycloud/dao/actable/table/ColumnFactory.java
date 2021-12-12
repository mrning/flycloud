package com.zac.flycloud.dao.actable.table;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 字段工厂
 *
 * @author starmark
 * @create 2018-04-07 12:52
 **/
public class ColumnFactory {
    public static Map<String, Class<CommonColumn>> columnMap = new HashMap<>();

    static {
        add(Date.class.getName(), DateColumn.class);
        add(Double.class.getName(), DoubleColumn.class);
        add(Integer.class.getName(), IntegerColumn.class);
        add(int.class.getName(), IntegerColumn.class);
        add(Long.class.getName(), LongColumn.class);
        add(long.class.getName(), LongColumn.class);
        add(boolean.class.getName(), BooleanColumn.class);
        add(Boolean.class.getName(), BooleanColumn.class);
        add(String.class.getName(), StringColumn.class);


    }

    /**
     * 增加字段类型
     *
     * @param javaType
     * @param columnClass
     */
    public static void add(String javaType, Class columnClass) {
        columnMap.put(javaType, columnClass);
    }

    public static Class<CommonColumn> getCommonColumn(
            String javaType) {

        return columnMap.get(javaType);
    }
}
