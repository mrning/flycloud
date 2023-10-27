#依赖的父镜像
FROM openjdk:17-jdk
#作者
MAINTAINER zac

ARG profile
ENV env=$profile
RUN echo $env
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Dfile.encoding=utf-8 --add-exports java.base/sun.security.pkcs=ALL-UNNAMED "
ENV port=8008
ENV module=pay
#修改时区
ENV TimeZone=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TimeZone /etc/localtime && echo $TimeZone > /etc/timezone
#jar包添加到镜像中
ADD target/htx-${module}.jar /opt/server/htx-${module}.jar
#容器暴露的端口 即jar程序在容器中运行的端口
EXPOSE ${port}
#容器启动之后要执行的命令
CMD java $JAVA_OPTS -jar -Dspring.profiles.active=$env /opt/server/htx-$module.jar
