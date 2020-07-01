package com.zac.flycloud.mapper;

import com.zac.flycloud.tablemodel.FcUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("SELECT * FROM fc_user WHERE username = #{username}")
    FcUser findByUserName(@Param("username") String username);

}
