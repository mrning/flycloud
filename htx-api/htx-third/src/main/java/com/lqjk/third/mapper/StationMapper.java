package com.lqjk.third.mapper;


import com.lqjk.third.beans.Station;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @sunqipeng
 **/
@Mapper
public interface StationMapper {

    Station findStationById(long id);

    List<Station> queryStationByName(@Param("stationName") String stationName);

    List<Station> queryAllStations();

    List<Station> queryStationByLineName(String lineName);

    Station findStationByItpCode(@Param("itpCode") String itpCode);

    List<Station> queryStationByMetroLine(@Param("metroLineId") Long metroLineId);

    Station queryStationByNameOne(@Param("stationName") String stationName);
    
    Station findExitStationByItpCode(@Param("itpCode") String itpCode);
}
