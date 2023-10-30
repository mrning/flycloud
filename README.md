# zacboot

基于spring cloud alibaba实现的微服务脚手架


### 核心依赖


- Jdk17 [下载地址](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- SpringBoot 3.1.3
- SpringCloud Alibaba 2022 + Nacos
- Sa-Token 1.34.0
- Mybatis + Mybatis-plus
- Mysql8
- Redis

### 模块说明
- zacboot-system
  - zacboot-gateway【路由鉴权】端口：8000
  - zacboot-auth【登录认证】端口：8001
  - zacboot-monitor【监控】端口：8101
  - zacboot-autocode【代码生成】端口：8009
- zacboot-common 项目核心依赖，按需引用
- zacboot-request 微服务feign定义和request + response 对象定义，方便引用
- zacboot-api
  - zacboot-admin【管理后台】端口：8010
  - zacboot-app【app专用接口】端口：8020
  - zacboot-pay【支付相关】端口：8008
  - zacboot-third【第三方对接相关】端口：8003

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
1. 【zacboot-request】依赖【zacboot-base】，其他项目引用zacboot-request
2. 在【zacboot-base】项目 com.zac.base.bizentity 包下面创建需要的entity类，并且继承BaseEntity
3. 启动【zacboot-autocode】 --> AutoCodeApplication 然后访问[swagger-ui](http://localhost:8009/doc.html)
4. 调用 `mybatis自动生成代码 >>> 根据实体类生成表结构(或者根据表结构生成代码)` 自动生成对应的CRUD代码 或者 根据指定包下的entity生成/更新数据库字段
5. 代码生成路径默认为项目根目录下的【gen-dir】文件夹里面
6. 接收前端时间参数使用 Date 类型，返回给前端时间结果时如果使用了 LocalDateTime 需要添加注解 @JsonFormat
7. 如果需要entity和request中的vo相互转换的话，将转换逻辑写到htx-request定义的请求实体里面，因为【1】

### 相关技术文档
- [Jdk17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Springboot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Springboot-security](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security)
- [Mybatis-spring](http://mybatis.org/spring/zh/)
- [Mybatis-plus](https://baomidou.com/guide/)
- [Dubbo](http://dubbo.apache.org/zh-cn/docs/user/quick-start.html)
- [Nacos](https://nacos.io/zh-cn/docs/quick-start.html)

### 部分部署命令
` git clone -b test https://xxx@gitlab.***.git `

` cd zacboot `

` mvn clean package -Dmaven.test.skip=true -DskipTests `

` docker-compose --env-file .env-test up zacboot-admin(如果无模块名称则启动全部，否则只启动单独镜像)  -d(是否置于后台运行) `# 启动镜像

` docker-compose --env-file .env-test config ` # 镜像配置

` docker-compose --env-file .env-test build `  # 镜像构建

` docker-compose --env-file .env-test down `   # 镜像停止 
`




