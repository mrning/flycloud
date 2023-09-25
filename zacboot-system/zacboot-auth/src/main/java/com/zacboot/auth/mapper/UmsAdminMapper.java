package com.zacboot.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zacboot.auth.beans.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2020-08-21
 */
public interface UmsAdminMapper extends BaseMapper<SysUser> {

    /**
     * 获取资源相关用户ID列表
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);

}
