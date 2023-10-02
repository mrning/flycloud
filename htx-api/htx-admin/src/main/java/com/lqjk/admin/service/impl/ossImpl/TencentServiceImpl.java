package com.lqjk.admin.service.impl.ossImpl;

import com.alibaba.fastjson.JSONObject;
import com.lqjk.admin.service.UploadFileService;
import org.springframework.web.multipart.MultipartFile;

public class TencentServiceImpl implements UploadFileService {

    @Override
    public JSONObject upload(MultipartFile file, String fileDir, String customBucket) {
        return null;
    }

    @Override
    public Object initOSS(String endpoint, String accessKeyId, String accessKeySecret) {
        return null;
    }
}
