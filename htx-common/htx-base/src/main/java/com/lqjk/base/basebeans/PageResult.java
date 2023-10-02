package com.lqjk.base.basebeans;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private Long total;
    private List<T> dataList;
}

