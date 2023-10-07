# htx

基于spirngcloud alibaba实现的微服务脚手架，实现了熔断，限流，gateway路由控制，sso登录认证，鉴权等功能，更多功能持续开发中


### 核心依赖


- Jdk17 [下载地址](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- SpringBoot 3.1.3
- SpringCloud Alibaba 2022 + Nacos
- Sa-Token 1.30.0
- Mybatis + Mybatis-plus
- Mysql8
- Redis

### 模块说明
- htx-system-gateway【路由鉴权】端口：8000
- htx-system-sso【登录认证】端口：8001
- htx-system-monitor【监控】端口：8101
- htx-system-autocode【代码生成】端口：8009
- htx-system-core【服务调用中间件】
- htx-common【公共模块，按需引用，无需启动】
- htx-admin【管理后台】端口：8010
- htx-app-user【app用户模块】端口：8020

### 安装教程
- 安装jdk17
- 安装mysql8 并启动
- 安装redis 并启动
- 安装elasticsearch 并启动 [安装地址](https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.0.1.msi)
- 搜索 application.yml 修改数据库配置
- 执行dbsql文件夹下的create-db.sql中的建库语句
- 启动AutoCodeApplication类中的main方法,日志输出swagger地址
- swagger中调用接口【Mybatis自动生成代码】-【根据实体类创建或更新表结构】
- 执行dbsql文件夹下的init-data.sql中的初始化语句
- 更多安装部署命令，请参照[安装命令详情](https://gitee.com/mrning001/zacbook/blob/master/dockertext.txt)

### 使用说明
 ##### 理想状态下，使用该平台的正确打开方式：
 1. 【htx-request】依赖【htx-base】，其他项目引用htx-request
 2. 在【htx-base】项目 com.lqjk.base.bizentity 包下面创建需要的entity类，并且继承BaseEntity
 3. 然后访问[swagger-ui](http://localhost:8009/doc.html)
 4. 调用 `mybatis自动生成代码 >>> 根据实体类生成表结构(或者根据表结构生成代码)` 自动生成对应的CRUD代码 或者 根据指定包下的entity生成/更新数据库字段
 5. 代码生成路径默认为项目根目录下的【gen-dir】文件夹里面
 6. 前端直接调用接口使用数据即可
 7. 接收前端时间参数使用 Date 类型，返回给前端时间结果时如果使用了 LocalDateTime 需要添加注解 @JsonFormat
 8. 如果需要entity和request中的vo相互转换的话，将转换逻辑写到htx-request定义的请求实体里面，因为【1】

### 相关技术文档
- [Jdk17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Springboot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Springboot-security](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security)
- [Mybatis-spring](http://mybatis.org/spring/zh/)
- [Mybatis-plus](https://baomidou.com/guide/)
- [Dubbo](http://dubbo.apache.org/zh-cn/docs/user/quick-start.html)
- [Nacos](https://nacos.io/zh-cn/docs/quick-start.html)




