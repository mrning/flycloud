package com.lqjk.third.mapper;

import com.lqjk.third.beans.StationDistance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 站间距
 * @author zhengkai.blog.csdn.net
 * @date 2021-06-16
 */
@Mapper
@Repository
public interface StationDistanceMapper {

	/**
	 * 新增
	 * @author zhengkai.blog.csdn.net
	 * @date 2021/06/16
	 **/
	int insert(StationDistance stationDistance);

	/**
	 * 刪除
	 * @author zhengkai.blog.csdn.net
	 * @date 2021/06/16
	 **/
	int delete(int id);

	/**
	 * 更新
	 * @author zhengkai.blog.csdn.net
	 * @date 2021/06/16
	 **/
	int update(StationDistance stationDistance);

	/**
	 * 查询 根据主键 id 查询
	 * @author zhengkai.blog.csdn.net
	 * @date 2021/06/16
	 **/
	StationDistance load(@Param("inStationId") String inStationId, @Param("outStationId") String outStationId);

	/**
	 * 查询 分页查询
	 * @author zhengkai.blog.csdn.net
	 * @date 2021/06/16
	 **/
	List<StationDistance> pageList(int offset, int pagesize);

	/**
	 * 查询 分页查询 count
	 * @author zhengkai.blog.csdn.net
	 * @date 2021/06/16
	 **/
	int pageListCount(int offset, int pagesize);



}
