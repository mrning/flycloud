package com.lqjk.third.mapper;

import com.lqjk.third.beans.AppThirdChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author libatou
 * @since 2023-01-30
 */
@Mapper
public interface AppThirdChannelMapper {

    AppThirdChannel selectById(Long id);

    List<AppThirdChannel> selectByAppThirdChannel(AppThirdChannel appThirdChannel);

    int insert(AppThirdChannel appThirdChannel);

    int update(AppThirdChannel appThirdChannel);

    int deleteById(Long id);

    AppThirdChannel selectByChannel(@Param("channel") String channel);
}
