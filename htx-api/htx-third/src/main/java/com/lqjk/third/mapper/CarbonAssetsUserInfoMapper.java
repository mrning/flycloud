package com.lqjk.third.mapper;

import com.lqjk.third.beans.CarbonAssetsUserInfo;
import com.lqjk.third.beans.CarbonAssetsUserInfoChannel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 个人碳资产账户表
 * @author nxr
 * @date 2021-06-01
 */
@Mapper
@Repository
public interface CarbonAssetsUserInfoMapper {

    List<CarbonAssetsUserInfoChannel> generalQuery(CarbonAssetsUserInfo carbonAssetsUserInfo);

    /**
     * 新增
     * @author nxr
     * @date 2021/06/01
     **/
    int insert(CarbonAssetsUserInfo carbonAssetsUserInfo);

    /**
     * 刪除
     * @author nxr
     * @date 2021/06/01
     **/
    int delete(int id);

    /**
     * 更新
     * @author nxr
     * @date 2021/06/01
     **/
    int update(CarbonAssetsUserInfo carbonAssetsUserInfo);
    //兑换后更新账户
    int updateExchange(CarbonAssetsUserInfo carbonAssetsUserInfo);

    int add(CarbonAssetsUserInfo carbonAssetsUserInfo);

    /**
     * 查询 根据主键 id 查询
     * @author nxr
     * @date 2021/06/01
     **/
    CarbonAssetsUserInfo load(int id);

}