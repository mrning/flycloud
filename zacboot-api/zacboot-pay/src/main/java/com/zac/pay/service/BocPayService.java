package com.zac.pay.service;

import cn.hutool.json.JSONObject;
import com.zac.request.req.pay.BocPayUpRequest;

public interface BocPayService {

    JSONObject payUp(BocPayUpRequest bocPayUpRequest);
}
