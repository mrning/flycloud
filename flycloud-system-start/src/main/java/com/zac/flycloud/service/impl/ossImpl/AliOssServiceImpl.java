package com.zac.flycloud.service.impl.ossImpl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;
import com.zac.flycloud.service.UploadFileService;
import com.zac.flycloud.bean.configbean.OssConfiguration;
import com.zac.flycloud.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
public class AliOssServiceImpl implements UploadFileService {

    @Autowired
    private OssConfiguration ossConfiguration;

    @Override
    public JSONObject upload(MultipartFile file, String fileDir, String customBucket) {
        JSONObject result = new JSONObject();
        OSSClient ossClient = initOSS(ossConfiguration.getEndpoint(), ossConfiguration.getAccessKeyId(),
                ossConfiguration.getAccessKeySecret());
        StringBuilder fileUrl = new StringBuilder();
        String newBucket = ossConfiguration.getBucketName();
        if (StringUtils.isNotEmpty(customBucket)) {
            newBucket = customBucket;
        }
        try {
            //判断桶是否存在,不存在则创建桶
            if (!ossClient.doesBucketExist(newBucket)) {
                ossClient.createBucket(newBucket);
            }
            // 获取文件名
            String orgName = file.getOriginalFilename();
            if ("" == orgName) {
                orgName = file.getName();
            }
            orgName = FileUtil.getFileName(orgName);
            String fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            if (!fileDir.endsWith("/")) {
                fileDir = fileDir.concat("/");
            }
            fileUrl = fileUrl.append(fileDir + fileName);

            if (StringUtils.isNotEmpty(ossConfiguration.getStaticDomain()) && ossConfiguration.getStaticDomain().toLowerCase().startsWith("http")) {
                result.put("FILE_URL",ossConfiguration.getStaticDomain() + "/" + fileUrl);
            } else {
                result.put("FILE_URL","https://" + newBucket + "." + ossConfiguration.getEndpoint() + "/" + fileUrl);
            }
            PutObjectResult upRes = ossClient.putObject(newBucket, fileUrl.toString(), file.getInputStream());
            // 设置权限(公开读)
            ossClient.setBucketAcl(newBucket, CannedAccessControlList.PublicRead);
            if (upRes != null) {
                log.info("------OSS文件上传成功------" + fileUrl);
                result.put("uploadAliOssRes",upRes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public OSSClient initOSS(String endpoint, String accessKeyId, String accessKeySecret) {
        return new OSSClient(endpoint,
                new DefaultCredentialProvider(accessKeyId, accessKeySecret),
                new ClientConfiguration());
    }
}
