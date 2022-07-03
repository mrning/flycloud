package com.zac.flycloud.common.basebeans;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private int total;
    private List<T> dataList;
}

