package com.lqjk.third.mapper;

import com.lqjk.third.beans.AppThirdAuth;
import com.lqjk.third.beans.UserAuthCardNo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 三方APP授权记录表 Mapper 接口
 * </p>
 *
 * @author libatou
 * @since 2021-11-19
 */
@Mapper
public interface AppThirdAuthMapper {

    AppThirdAuth selectById(Long id);

    List<AppThirdAuth> selectByAppThirdAuth(AppThirdAuth appThirdAuth);

    int insert(AppThirdAuth appThirdAuth);

    int update(AppThirdAuth appThirdAuth);

    int deleteById(Long id);

    Map<String,Object> findOwnerId(@Param("cardNo") String cardNo, @Param("authType") String authType, @Param("appChannel") String appChannel);

    void insertUserAuthCardNo(@Param("ownerId")Long ownerId, @Param("cardNoList")List<String> cardNoList,@Param("userNo") String userNo);

    void deleteCardNo(@Param("ownerId")Long ownerId,  @Param("authId") Long thirdAuthId);

    void cancelUserAuthCardNo(@Param("ownerId") Long ownerId, @Param("authId") Long id,@Param("cardNoList") List<String> cancelCardNoList);

    UserAuthCardNo findByCardNo(String cardNo);

    List<String> getCardNoList(@Param("ownerId")Long ownerId, @Param("authId")Long id);
}
