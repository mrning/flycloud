package com.zacboot.api.mini.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zacboot.api.mini.beans.dtos.MiniUserDto;

/**
 * @Description: 底层共通业务API，提供其他独立模块调用
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 日志添加
     *
     * @param LogContent  内容
     * @param logType     日志类型(0:操作日志;1:登录日志;2:定时任务)
     * @param operatetype 操作类型(1:添加;2:修改;3:删除;)
     */
    void addLog(String LogContent, Integer logType, Integer operatetype);

    /**
     * 根据用户uuid查询用户信息
     *
     * @param uuid
     * @return
     */
    MiniUserDto getUserByUuid(String uuid);

}
