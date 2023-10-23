package com.lqjk.third.service;

import com.lqjk.third.beans.BusTakeRecord;

/**
 * @ClassName BusTakeRecordService
 * @Description 公交行程记录
 * @Author YJD
 * @Date 2023/3/6 11:18
 * @Version 1.0
 */
public interface BusTakeRecordService {

    void uploadBusInfo(BusTakeRecord busTakeRecord);

    Long sumCarbon(Long ownerId,String source);

}
