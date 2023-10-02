package com.lqjk.base.basebeans;

import lombok.Data;

@Data
public class PageRequest {

    private Long page = 1L;

    private Long pageSize = 10L;
}
