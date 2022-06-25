package com.zac.flycloud.start.bean.basebean;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private int total;
    private List<T> dataList;
}

