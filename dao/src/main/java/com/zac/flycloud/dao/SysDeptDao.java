package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.dto.SysDeptDTO;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysDeptDao {
    Integer add(SysDeptDTO sysDeptDTO);

    Integer del(SysDeptDTO sysDeptDTO);

    Integer update(SysDeptDTO sysDeptDTO);

    List<SysDeptDTO> queryPage(SysDeptDTO sysDeptDTO, Page page);

    Long queryPageCount(SysDeptDTO sysDeptDTO);
}