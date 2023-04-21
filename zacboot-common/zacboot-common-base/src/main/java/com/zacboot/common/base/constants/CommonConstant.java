package com.zacboot.common.base.constants;

import java.util.HashMap;
import java.util.Map;

public interface CommonConstant {

    /**
     * 系统日志类型： 登录
     */
    int LOG_TYPE_LOGIN = 1;

    /**
     * 登出
     */
    int LOG_TYPE_LOGOUT = 2;

    /**
     * 系统日志类型： 操作
     */
    int LOG_TYPE_OPERATION_2 = 2;

    /**
     * 操作日志类型： 查询
     */
    int OPERATE_TYPE_QUERY_1 = 1;

    /**
     * 操作日志类型： 添加
     */
    int OPERATE_TYPE_ADD_2 = 2;

    /**
     * 操作日志类型： 更新
     */
    int OPERATE_TYPE_UPDATE_3 = 3;

    /**
     * 操作日志类型： 删除
     */
    int OPERATE_TYPE_DELETE_4 = 4;

    /**
     * 操作日志类型： 导入
     */
    int OPERATE_TYPE_IMPORT_5 = 5;

    /**
     * 操作日志类型： 导出
     */
    int OPERATE_TYPE_EXPORT_6 = 6;

    /**
     * {@code 404 Server Error} (HTTP/1.0 - RFC 1945)
     */
    Integer SC_RESOURCE_NOTFOUND_ERROR_404 = 404;

    /**
     * {@code 500 Server Error} (HTTP/1.0 - RFC 1945)
     */
    Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /**
     * {@code 200 OK} (HTTP/1.0 - RFC 1945)
     */
    Integer SC_OK_200 = 200;

    /**
     * 访问权限认证未通过 510
     */
    Integer SC_NO_AUTH = 510;

    Integer UNAUTHORIZED = 401;

    /**
     * 登录用户Token令牌缓存KEY前缀
     */
    String PREFIX_USER_TOKEN = "prefix_user_token_";
    /**
     * Token缓存时间：3600秒即一小时
     */
    int TOKEN_EXPIRE_TIME = 2 * 3600;


    /**
     * 0：一级菜单
     */
    Integer MENU_TYPE_0 = 0;
    /**
     * 1：子菜单
     */
    Integer MENU_TYPE_1 = 1;
    /**
     * 2：按钮权限
     */
    Integer MENU_TYPE_2 = 2;

    /**
     * 状态(0无效1有效)
     */
    String STATUS_0 = "0";
    String STATUS_1 = "1";

    /**
     * 同步工作流引擎1同步0不同步
     */
    Integer ACT_SYNC_1 = 1;
    Integer ACT_SYNC_0 = 0;

    /**
     * 消息类型1:通知公告2:系统消息
     */
    String MSG_CATEGORY_1 = "1";
    String MSG_CATEGORY_2 = "2";

    /**
     * 文件上传类型（本地：local，Minio：minio，阿里云：alioss）
     */
    String UPLOAD_TYPE_LOCAL = "local";
    String UPLOAD_TYPE_MINIO = "minio";
    String UPLOAD_TYPE_OSS = "alioss";

    /**
     * 文档上传自定义桶名称
     */
    String UPLOAD_CUSTOM_BUCKET = "eoafile";
    /**
     * 文档上传自定义路径
     */
    String UPLOAD_CUSTOM_PATH = "eoafile";
    /**
     * 文件外链接有效天数
     */
    Integer UPLOAD_EFFECTIVE_DAYS = 1;

    /**
     * 员工身份 （1:普通员工  2:上级）
     */
    Integer USER_IDENTITY_1 = 1;
    Integer USER_IDENTITY_2 = 2;

    /**
     * 接口url
     */
    Map<String,String> URL_MAPPING_MAP = new HashMap<>();

    /**
     * 请求头 - token
     */
    String REQUEST_HEADER_TOKEN = "token";
    String JWT_PAYLOAD_USERUUID = "useruuid";
    String JWT_PAYLOAD_USERNAME = "username";
    String JWT_PAYLOAD_ROLES = "roles";
    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";
    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "admin-web";

    /**
     * 前台商城client_id
     */
    String APP_CLIENT_ID = "app";

    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN = "/api-admin/**";

    /**
     * app接口路径匹配
     */
    String APP_URL_PATTERN = "/api-app/**";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "user";

    /**
     * 请求头类型：
     * application/x-www-form-urlencoded ： form表单格式
     * application/json ： json格式
     */
    String REQUEST_HEADERS_JSON = "application/json";

    String REQUEST_HEADERS_FORM = "application/x-www-form-urlencoded";
}
