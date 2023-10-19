package com.lqjk.pay.service;

import cn.hutool.json.JSONObject;
import com.lqjk.request.req.pay.BocPayUpRequest;

public interface BocPayService {

    JSONObject payUp(BocPayUpRequest bocPayUpRequest);
}
