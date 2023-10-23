package com.lqjk.third.mapper;

import com.lqjk.third.beans.CarbonAssetsUserInfoChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author YJD
 * @Date 2023/3/15 15:08
 * @Version 1.0
 */
@Mapper
@Repository
public interface CarbonAssetsUserInfoChannelMapper {
    List<CarbonAssetsUserInfoChannel> generalQuery(CarbonAssetsUserInfoChannel carbonAssetsUserInfoChannel);

    int insert(CarbonAssetsUserInfoChannel carbonAssetsUserInfoChannel);

    int add(CarbonAssetsUserInfoChannel carbonAssetsUserInfoChannel);

    CarbonAssetsUserInfoChannel load(@Param("id") Long id);
}
