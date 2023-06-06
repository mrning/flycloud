# ZacBoot

基于spirngcloud alibaba实现的微服务脚手架，实现了熔断，限流，gateway路由控制，sso登录认证，鉴权等功能，更多功能持续开发中


### 软件架构
- Jdk17 [下载地址](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- SpringBoot 2.7.0
- SpringCloud Alibaba 2021.1 + Nacos
- Sa-Token 1.30.0
- Mybatis + Mybatis-plus
- Mysql8
- Redis

### 模块说明
- zacboot-system-gateway【路由鉴权】端口：8000
- zacboot-system-sso【登录认证】端口：8001
- zacboot-system-monitor【监控】端口：8101
- zacboot-system-autocode【代码生成】端口：8009
- zacboot-system-core【服务调用中间件】
- zacboot-common【公共模块，按需引用，无需启动】
- zacboot-admin【管理后台】端口：8010
- zacboot-app-user【app用户模块】端口：8020

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
 1. 【zacboot-system-core】依赖【zacboot-common-base】，其他项目引用system-core
 2. 在【zacboot-system-core】项目 com.zac.system.core.entity.admin包下面创建需要的entity类，并且继承BaseEntity
 3. 然后访问[swagger-ui](http://localhost:9088/swagger-ui/index.html)
 4. 调用 `mybatis自动生成代码 >>> 代码生成入口` 自动生成对应的CRUD代码 或者 根据指定包下的entity生成/更新数据库字段
 5. 代码生成路径默认为项目根目录下的【gen-dir】文件夹里面
 6. 前端直接调用接口使用数据即可
 7. 接收前端时间参数使用 Date 类型，返回给前端时间结果时如果使用了 LocalDateTime 需要添加注解 @JsonFormat

### 相关技术文档
- [Jdk17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Springboot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Springboot-security](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security)
- [Mybatis-spring](http://mybatis.org/spring/zh/)
- [Mybatis-plus](https://baomidou.com/guide/)
- [Dubbo](http://dubbo.apache.org/zh-cn/docs/user/quick-start.html)
- [Nacos](https://nacos.io/zh-cn/docs/quick-start.html)




