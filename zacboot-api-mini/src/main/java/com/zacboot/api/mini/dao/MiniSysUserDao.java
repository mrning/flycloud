package com.zacboot.api.mini.dao;

import cn.hutool.db.Page;
import com.zacboot.system.core.entity.mini.MiniUser;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年3月19日星期日
 * @author zac
 */
public interface MiniSysUserDao {
    Integer add(MiniUser miniUser);

    Integer del(MiniUser miniUser);

    Integer update(MiniUser miniUser);

    MiniUser queryByOpenId(String openId);

    List<MiniUser> queryPage(MiniUser miniUser, Page page);

    Long queryPageCount(MiniUser miniUser);
}