package com.zac.flycloud.basebean;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private int total;
    private List<T> dataList;
}

