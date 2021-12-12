package com.zac.flycloud.dao;

import cn.hutool.db.Page;
import com.zac.flycloud.bean.dto.SysDeptDTO;
import com.zac.flycloud.bean.vos.DeptRequestVO;

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

    List<SysDeptDTO> queryPage(DeptRequestVO deptRequestVO, Page page);

    Long queryPageCount(DeptRequestVO deptRequestVO);
}