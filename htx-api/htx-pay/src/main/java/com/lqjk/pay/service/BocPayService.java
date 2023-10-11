package com.lqjk.pay.service;

import com.lqjk.request.req.pay.BocPayUpRequest;

public interface BocPayService {

    String payUp(BocPayUpRequest bocPayUpRequest);
}
