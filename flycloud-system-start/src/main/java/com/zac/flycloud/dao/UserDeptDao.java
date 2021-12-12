package com.zac.flycloud.dao;


import com.zac.flycloud.bean.tablemodel.SysDept;

import java.util.List;

public interface UserDeptDao {

    List<SysDept> getDeptsByUserUuid(String userUuid);
}
