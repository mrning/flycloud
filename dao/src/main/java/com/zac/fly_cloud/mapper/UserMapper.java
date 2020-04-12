package com.zac.fly_cloud.mapper;

import com.zac.fly_cloud.tablemodel.FcUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("SELECT * FROM fc_user WHERE username = #{username}")
    FcUser findByUserName(@Param("username") String username);

}
