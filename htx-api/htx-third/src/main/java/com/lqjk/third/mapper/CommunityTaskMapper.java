package com.lqjk.third.mapper;

import com.lqjk.third.beans.CommunityTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HZY
 * @description 至臻社区任务
 * @date 2021-06-01
 */
@Mapper
@Repository
public interface CommunityTaskMapper {

    /**
     * 查询 根据主键 id 查询
     *
     * @author HZY
     * @date 2021/06/01
     **/
    CommunityTask load(@Param("id") String id, @Param("appChannel") String appChannel);

    CommunityTask queryUsableTask(@Param("type") String type, @Param("date") String date, @Param("taskType") String taskType, @Param("scene") String scene, @Param("appChannel") String appChannel);


    /**
     * 查询
     *
     * @author HZY
     * @date 2021/06/01
     **/
    List<CommunityTask> queryList(@Param("type") String type,
                                  @Param("date") String date,
                                  @Param("taskType") String taskType,
                                  @Param("ownerId") Long ownerId,
                                  @Param("areaCode") String areaCode,
                                  @Param("appChannel") String appChannel,
                                  @Param("beginning") String beginning,
                                  @Param("newUserFlag") Boolean newUserFlag
    );

    List<CommunityTask> queryByAll(CommunityTask communityTask);


    List<CommunityTask> selectLinkUrl(@Param("metroTaskId") String metroTaskId, @Param("busTaskId") String busTaskId, @Param("helloBikeTaskId") String helloBikeTaskId);

    List<CommunityTask> selectLinkUrlHtx(@Param("metroTaskId") String metroTaskId, @Param("busTaskId") String busTaskId, @Param("helloBikeTaskId") String helloBikeTaskId);

    List<String> getUserCarbonYear(Long ownerId);
}