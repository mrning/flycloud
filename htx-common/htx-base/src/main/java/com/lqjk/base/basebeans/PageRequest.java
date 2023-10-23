package com.lqjk.base.basebeans;

import lombok.Data;

@Data
public class PageRequest {

    private Integer page = 1;

    private Integer pageSize = 10;
}
