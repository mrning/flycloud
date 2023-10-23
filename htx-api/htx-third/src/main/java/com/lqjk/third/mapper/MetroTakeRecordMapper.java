package com.lqjk.third.mapper;

import com.lqjk.third.beans.MetroTakeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description 行程记录表
 * @author liuchijng
 * @date 2021-06-01
 */
@Mapper
@Repository
public interface MetroTakeRecordMapper {

	/**
	 * 新增
	 * @author liuchijng
	 * @date 2021/06/01
	 **/
	int insert(MetroTakeRecord metroTakeRecord);

	/**
	 * 刪除
	 * @author liuchijng
	 * @date 2021/06/01
	 **/
	int delete(int id);

	/**
	 * 更新
	 * @author liuchijng
	 * @date 2021/06/01
	 **/
	int update(MetroTakeRecord metroTakeRecord);
	
	int updateLongevityChainFlag(MetroTakeRecord metroTakeRecord);

	/**
	 * 查询 根据主键 id 查询
	 * @author liuchijng
	 * @date 2021/06/01
	 **/
	MetroTakeRecord load(@Param("orderNo") String orderNo);
	
	/**
	 * 查询 根据主键 id 查询
	 * @author liuchijng
	 * @date 2021/06/01
	 **/
	MetroTakeRecord loadById(Long id);

	/**
	 * 获取待发送上链消息队列的数据信息
	 * @return
	 */
	List<MetroTakeRecord> querySendAbleInfoList(MetroTakeRecord cond);

	Map<String, Object> selectStatsData(@Param("ownerId") Long ownerId, @Param("createdTime") LocalDateTime createdTime);

	MetroTakeRecord getMaxTransMileageData(Long ownerId);

	BigDecimal sumTransMileage(Long ownerId);

    void updateStatus(MetroTakeRecord metroTakeRecord);

	Long sumCarbon(@Param("ownerId")Long ownerId,@Param("source")String source);
}
