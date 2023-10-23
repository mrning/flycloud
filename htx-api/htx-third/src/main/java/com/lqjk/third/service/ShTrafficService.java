package com.lqjk.third.service;

import com.lqjk.third.beans.AuthUserVo;
import com.lqjk.third.beans.CustomerLogin;
import com.lqjk.third.beans.ShTradeInfoVo;
import com.lqjk.third.beans.UserAuthCardNo;

import java.util.List;

/**
 * @ClassName ShTrafficService
 * @Description TODO
 * @Author YJD
 * @Date 2023/4/6 15:39
 * @Version 1.0
 */
public interface ShTrafficService {
    /**
     * 授权
     * @param authUser
     * @return
     */
    String authorization(AuthUserVo authUser, List<String> cardNoList);
    /**
     * 取消授权
     * @param authUser
     * @return
     */
    String cancelAuth(AuthUserVo authUser);

    String authModify(List<AuthUserVo> authList);

    void uploadMetroInfo(ShTradeInfoVo shTradeInfoVo, UserAuthCardNo userAuthCardNo);

    void uploadBusInfo(ShTradeInfoVo shTradeInfoVo, UserAuthCardNo userAuthCardNo);

    String changePhone(String userToken, String mobile);

    UserAuthCardNo getOwnerIdByToken(String token);
}
