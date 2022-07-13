package com.zacboot.common.base.basebeans;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private int total;
    private List<T> dataList;
}

