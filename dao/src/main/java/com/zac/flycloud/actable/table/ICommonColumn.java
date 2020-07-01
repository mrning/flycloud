package com.zac.flycloud.actable.table;

/**
 * 通用字段接口类
 *
 **/
public interface ICommonColumn {

    String getName();

    String getType();

    int getLength();

    int getDecimalLength();

    boolean isNullValue();

    boolean isKey();

    boolean isAutoIncrement();

    String getDefaultValue();


}
