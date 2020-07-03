package com.zac.flycloud.base;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.zac.flycloud.entity.tablemodel.SysDept;
import com.zac.flycloud.entity.tablemodel.SysRole;
import com.zac.flycloud.entity.tablemodel.SysUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 底层共通业务API，提供其他独立模块调用
 * @Author: scott
 * @Date:2019-4-20 
 * @Version:V1.0
 */
public interface SysBaseAPI {

	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param logType 日志类型(0:操作日志;1:登录日志;2:定时任务)
	 * @param operatetype 操作类型(1:添加;2:修改;3:删除;)
	 */
	void addLog(String LogContent, Integer logType, Integer operatetype);
	
	/**
	  * 根据用户账号查询用户信息
	 * @param username
	 * @return
	 */
    SysUser getUserByName(String username);
	
	/**
	  * 根据用户id查询用户信息
	 * @param id
	 * @return
	 */
    SysUser getUserById(String id);
	
	/**
	 * 通过用户账号查询角色集合
	 * @param username
	 * @return
	 */
    List<String> getRolesByUsername(String username);

	/**
	 * 通过用户账号查询部门集合
	 * @param username
	 * @return 部门 id
	 */
	List<String> getDepartIdsByUsername(String username);

	/**
	 * 通过用户账号查询部门 name
	 * @param username
	 * @return 部门 name
	 */
	List<String> getDepartNamesByUsername(String username);

	/**
	 * 获取当前数据库类型
	 * @return
	 * @throws Exception 
	 */
    String getDatabaseType() throws SQLException;

    /**
   	 * 查询所有部门，拼接查询条件
   	 * @return
   	 */
   	List<JSONObject> queryAllDepart(Wrapper wrapper);

	/**
	 * 发送系统消息
	 * @param fromUser 发送人(用户登录账户)
	 * @param toUser  发送给(用户登录账户)
	 * @param title  消息主题
	 * @param msgContent  消息内容
	 */
    void sendSysAnnouncement(String fromUser, String toUser, String title, String msgContent);

	/**
	 * 发送系统消息
	 * @param fromUser 发送人(用户登录账户)
	 * @param toUser   发送给(用户登录账户)
	 * @param title    通知标题
	 * @param map  	   模板参数
	 * @param templateCode  模板编码
	 */
    void sendSysAnnouncement(String fromUser, String toUser, String title, Map<String, String> map, String templateCode);

	/**
	 *
	 * @param fromUser 发送人(用户登录账户)
	 * @param toUser 发送给(用户登录账户)
	 * @param title 通知标题
	 * @param map 模板参数
	 * @param templateCode 模板编码
	 * @param busType 业务类型
	 * @param busId 业务id
	 */
    void sendSysAnnouncement(String fromUser, String toUser, String title, Map<String, String> map, String templateCode, String busType, String busId);

	/**
	 * 通过消息中心模板，生成推送内容
	 *
	 * @param templateCode 模板编码
	 * @param map          模板参数
	 * @return
	 */
    String parseTemplateByCode(String templateCode, Map<String, String> map);


	/**
	 * 发送系统消息
	 * @param fromUser 发送人(用户登录账户)
	 * @param toUser  发送给(用户登录账户)
	 * @param title  消息主题
	 * @param msgContent  消息内容
	 * @param setMsgCategory  消息类型 1:消息2:系统消息
	 */
    void sendSysAnnouncement(String fromUser, String toUser, String title, String msgContent, String setMsgCategory);

	/**queryTableDictByKeys
	 * 发送系统消息
	 * @param fromUser 发送人(用户登录账户)
	 * @param toUser  发送给(用户登录账户)
	 * @param title  消息主题
	 * @param msgContent  消息内容
	 * @param setMsgCategory  消息类型 1:消息2:系统消息
	 * @param busType  业务类型
	 * @param busId  业务id
	 */
    void sendSysAnnouncement(String fromUser, String toUser, String title, String msgContent, String setMsgCategory, String busType, String busId);

	/**
	 * 根据业务类型及业务id修改消息已读
	 * @param busType
	 * @param busId
	 */
    void updateSysAnnounReadFlag(String busType, String busId);

	/**
	 * 获取所有有效用户
	 * @return
	 */
    List<SysUser> queryAllUser();

    /**
     * 获取所有有效用户 带参
     * userIds 默认选中用户
     * @return
     */
    JSONObject queryAllUser(String[] userIds, int pageNo, int pageSize);

    /**
     * 获取所有有效用户 拼接查询条件
     *
     * @return
     */
    List<JSONObject> queryAllUser(Wrapper wrapper);

	/**
	 * 获取所有角色
	 * @return
	 */
    List<SysRole> queryAllRole();

	/**
	 * 获取所有角色 带参
     * roleIds 默认选中角色
	 * @return
	 */
    List<SysRole> queryAllRole(String[] roleIds);

	/**
	 * 通过用户账号查询角色Id集合
	 * @param username
	 * @return
	 */
    List<String> getRoleIdsByUsername(String username);

	/**
	 * 通过部门编号查询部门id
	 * @param orgCode
	 * @return
	 */
    String getDepartIdsByOrgCode(String orgCode);

	/**
	 * 查询上一级部门
	 * @param departId
	 * @return
	 */
    SysDept getParentDepartId(String departId);

	/**
	 * 查询所有部门
	 * @return
	 */
    List<SysDept> getAllSysDepart();

	/**
	 * 根据部门Id获取部门负责人
	 * @param deptId
	 * @return
	 */
    List<String> getDeptHeadByDepId(String deptId);

	/**
	 * 文件上传
	 * @param file 文件
	 * @param bizPath 自定义路径
	 * @param uploadType 上传方式
	 * @return
	 */
    String upload(MultipartFile file, String bizPath, String uploadType);

	/**
	 * 文件上传 自定义桶
	 * @param file
	 * @param bizPath
	 * @param uploadType
	 * @param customBucket
	 * @return
	 */
    String upload(MultipartFile file, String bizPath, String uploadType, String customBucket);

	/**
	 * 文档管理文件下载预览
	 * @param filePath
	 * @param uploadpath
	 * @param response
	 */
    void viewAndDownload(String filePath, String uploadpath, String uploadType, HttpServletResponse response);


	/**
	 * 给指定用户发消息
	 * @param userIds
	 * @param cmd
	 */
    void sendWebSocketMsg(String[] userIds, String cmd);

}
