package com.zacboot.admin.work.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zac.system.core.response.weixin.QwUserVo;
import com.zacboot.admin.work.service.WeixinService;
import com.zacboot.admin.work.weixin.WeixinWorkApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WeixinServiceImpl implements WeixinService {

    @Autowired
    private WeixinWorkApi weixinWorkApi;

    @Override
    public List<QwUserVo> getWxUsers() {
        List<QwUserVo> userVos = new ArrayList<>();
        JSONArray deptArr = weixinWorkApi.getDeptList();
        for (int i = 0; i < deptArr.size(); i++) {
            JSONObject deptObj = deptArr.getJSONObject(i);
            JSONArray userArr = weixinWorkApi.getUserListByDeptId(deptObj.getInteger("id"));
            for (Object u : userArr){
                userVos.add(JSONObject.parseObject(u.toString(),QwUserVo.class));
            }
        }
        return userVos;
    }
}
