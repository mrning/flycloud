package com.lqjk.third.mapper;

import com.lqjk.third.beans.BusTakeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author nxr
 * @description 公交行程记录表
 * @date 2021-08-06
 */
@Mapper
@Repository
public interface BusTakeRecordMapper {

	int insert(BusTakeRecord busTakeRecord);

	BusTakeRecord load(String orderNo);

	List<BusTakeRecord> queryInfoList(BusTakeRecord cond);

	BusTakeRecord loadById(Long id);

	int updateLongevityChainFlag(BusTakeRecord entity);

	HashMap<String, Object> selectStatsData(@Param("ownerId") Long ownerId, @Param("createdTime") String createdTime);

	int updateStatus(BusTakeRecord busTakeRecord);

    Long sumCarbon(@Param("ownerId")Long ownerId,@Param("source")String source);
}
