package com.zacboot.admin.work.weixin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.common.base.utils.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.zacboot.admin.work.weixin.constants.WeixinApiRedisKey.*;
import static com.zacboot.admin.work.weixin.constants.WeixinWorkUrl.*;


/**
 * 获取企业微信接口数据
 */
@Slf4j
@Component
public class WeixinWorkApi {

    @Value("${weixin.work.corpid:''}")
    private String corpid;

    @Value("${weixin.work.corpsecret:''}")
    private String corpsecret;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 获取access token
     * @return
     */
    public String getAccessToken(){
        Object tokenCache = redisUtil.get(TOKEN_CACHE_KEY);
        if (null != tokenCache){
            return tokenCache.toString();
        }

        JSONObject param = new JSONObject();
        param.put("corpid",corpid);
        param.put("corpsecret",corpsecret);
        JSONObject result = RestUtil.get(GET_TOKEN_URL, param);
        log.info(GET_TOKEN_URL + " result = {}", result.toString());

        if (0 != result.getInteger("errcode")){
            log.error(GET_TOKEN_URL + " request error = {}",result.getInteger("errmsg"));
        }

        String token = result.getString("access_token");
        redisUtil.set(TOKEN_CACHE_KEY,token, result.getLongValue("expires_in"));
        return token;
    }

    /**
     * 获取部门列表
     * @return
     */
    public JSONArray getDeptList(){
        Object tokenCache = redisUtil.get(DEPT_LIST_CACHE_KEY);
        if (null != tokenCache){
            return (JSONArray) tokenCache;
        }

        String token = getAccessToken();
        JSONObject param = new JSONObject();
        param.put("access_token",token);
        // 部门id，不填获取全量组织架构
        // param.put("id",corpsecret);
        JSONObject result = RestUtil.get(GET_DEPT_LIST_URL, param);
        log.info(GET_DEPT_LIST_URL + " result = {}", result.toString());

        if (0 != result.getInteger("errcode")){
            log.error(GET_DEPT_LIST_URL + " request error = {}",result.getInteger("errmsg"));
        }

        JSONArray deptDetailList = new JSONArray();
        for (Object dept : result.getJSONArray("department_id")){
            JSONObject deptObj = (JSONObject) dept;
            Integer deptId = deptObj.getInteger("id");

            JSONObject p = new JSONObject();
            p.put("access_token",token);
            p.put("id",deptId);
            // 获取单个部门详情
            JSONObject deptDetailRes = RestUtil.get(GET_DEPT_DETAIL_URL, p);
            log.info(GET_DEPT_DETAIL_URL + " result = {}", deptDetailRes.toString());

            if (0 != result.getInteger("errcode")){
                log.error(GET_DEPT_DETAIL_URL + " request error = {}",result.getInteger("errmsg"));
            }

            JSONObject d = deptDetailRes.getJSONObject("department");
            // 合并部门信息
            for(String key : d.keySet()){
                deptObj.put(key, d.get(key));
            }
            deptDetailList.add(deptObj);
        }

        // 部门列表信息缓存一周
        redisUtil.set(DEPT_LIST_CACHE_KEY, deptDetailList, 7*24*3600);
        return deptDetailList;
    }

    /**
     * 根据部门id获取部门成员
     */
    public JSONArray getUserListByDeptId(Integer deptId){
        String token = getAccessToken();

        JSONObject param = new JSONObject();
        param.put("access_token",token);
        param.put("department_id",deptId);
        JSONObject result = RestUtil.get(GET_USER_LIST_BY_DEPT, param);
        log.info(GET_USER_LIST_BY_DEPT + " result = {}", result.toString());
        if (0 != result.getInteger("errcode")){
            log.error(GET_USER_LIST_BY_DEPT + " request error = {}",result.getInteger("errmsg"));
        }
        /**
         * {
         *    "errcode": 0,
         *    "errmsg": "ok",
         *    "userlist": [
         *       {
         *           "userid": "zhangsan",
         *           "name": "张三",
         *           "department": [1, 2],
         * 		     "open_userid": "xxxxxx"
         *       }
         *     ]
         * }
         */
        return result.getJSONArray("userlist");
    }
}
