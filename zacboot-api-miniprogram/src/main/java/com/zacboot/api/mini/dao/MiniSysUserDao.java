package com.zacboot.api.mini.dao;

import cn.hutool.db.Page;
import com.zac.system.core.entity.mini.MiniUserEntity;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2023年3月19日星期日
 * @author zac
 */
public interface MiniSysUserDao {
    Integer add(MiniUserEntity miniUser);

    Integer del(MiniUserEntity miniUser);

    Integer update(MiniUserEntity miniUser);

    MiniUserEntity queryByOpenId(String openId);

    List<MiniUserEntity> queryPage(MiniUserEntity miniUser, Page page);

    Long queryPageCount(MiniUserEntity miniUser);
}