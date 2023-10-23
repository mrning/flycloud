package com.lqjk.third.service;

import com.lqjk.third.beans.MetroTakeRecord;

/**
 * @ClassName MetroTakeRecordService
 * @Description TODO
 * @Author YJD
 * @Date 2023/4/10 15:03
 * @Version 1.0
 */
public interface MetroTakeRecordService {
    void uploadMetroInfo(MetroTakeRecord metroTakeRecord);

    Long sumCarbon(Long ownerId,String source);

}
