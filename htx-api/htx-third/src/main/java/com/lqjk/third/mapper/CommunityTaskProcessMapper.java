package com.lqjk.third.mapper;

import com.lqjk.third.beans.CommunityTaskProcess;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description 社区任务参与过程
 * @author HZY
 * @date 2021-06-01
 */
@Mapper
@Repository
public interface CommunityTaskProcessMapper {

    /**
    * 新增
    * @author HZY
    * @date 2021/06/01
    **/
    int insert(CommunityTaskProcess communityTaskProcess);

    /**
    * 刪除
    * @author HZY
    * @date 2021/06/01
    **/
    int delete(int id);

    /**
    * 更新
    * @author HZY
    * @date 2021/06/01
    **/
    int update(CommunityTaskProcess communityTaskProcess);
}