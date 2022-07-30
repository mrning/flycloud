# Zacboot

#### 介绍
飞云平台
https://gitee.com/macrozheng/mall-tiny

#### 软件架构
- Jdk17 [下载地址](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- SpringBoot 2.6.0
- Spring-security
- Mybatis + Mybatis-plus
- Mysql8
- Dubbo + Nacos
- Redis

#### 安装教程
- 安装jdk17
- 安装mysql8 并启动
- 安装redis 并启动
- 安装elasticsearch 并启动 [安装地址](https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.0.1.msi)
- 搜索 application.yml 修改数据库配置
- 执行dbsql文件夹下的create-db.sql中的建库语句
- 启动AutoCodeApplication类中的main方法,日志输出swagger地址
- swagger中调用接口【Mybatis自动生成代码】-【根据实体类创建或更新表结构】
- 执行dbsql文件夹下的init-data.sql中的初始化语句

#### 使用说明
 ##### 开发规范
 1. com.zac.boot.entity.tablemodel包下面的bean（后缀为DTO）只用于创建或者更新数据库字段，不做业务使用
 ##### 理想状态下，使用该平台的正确打开方式：
 1. 在com.zac.boot.entity.tablemodel包下面创建需要的entity类，并且继承BaseEntity
 3. 然后访问[swagger-ui](http://localhost:9088/swagger-ui/index.html)
 4. 调用 `mybatis自动生成代码 >>> 代码生成入口` 自动生成对应的CRUD代码 或者 根据指定包下的entity生成/更新数据库字段
 5. 前端直接调用接口使用数据即可
 
 ##### 部分代码结构说明
 - 登录过滤逻辑：[]
 - 全局请求过滤：[AuthenticationFilter]认证 -> [Controller] 处理
    

#### 相关技术文档
- [Jdk17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Springboot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Springboot-security](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security)
- [Mybatis-spring](http://mybatis.org/spring/zh/)
- [Mybatis-plus](https://baomidou.com/guide/)
- [Dubbo](http://dubbo.apache.org/zh-cn/docs/user/quick-start.html)
- [Nacos](https://nacos.io/zh-cn/docs/quick-start.html)




