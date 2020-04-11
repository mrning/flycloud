package com.zac.fly_cloud.actable.table;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 字段SQL
 *
 * @author starmark
 * @create 2018-04-07 14:12
 **/
@Data
public class ColumnSql implements Serializable {

    public ColumnSql(CommonColumn column, List<String> primaryKeys) {
        this.setFieldName(column.getName());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(column.getType());

        if (column.getLength() > 0) {
            if (column.getDecimalLength() > 0) {
                stringBuilder.append("(").append(column.getLength()).append(",").append(column.getDecimalLength()).append(")");
            } else {
                stringBuilder.append("(").append(column.getLength()).append(")");
            }
        }
        stringBuilder.append(" ");
        if (column.isNullValue() && !primaryKeys.contains(column.getName())) {
            stringBuilder.append("NULL");
        } else {
            stringBuilder.append("NOT NULL");
        }
        if (column.isAutoIncrement()) {
            stringBuilder.append(" ");
            stringBuilder.append("AUTO_INCREMENT");
        }
        if (StringUtils.isNoneEmpty(column.getDefaultValue())) {
            stringBuilder.append(" ");
            stringBuilder.append("DEFAULT " + column.getDefaultValue());
        }
        this.setFieldType(stringBuilder.toString());
    }

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段名称
     */
    private String fieldName;
}
