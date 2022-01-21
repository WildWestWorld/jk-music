# 写在最前面：强烈建议先阅读官方教程[Dockerfile最佳实践]（https://docs.docker.com/develop/develop-images/dockerfile_best-practices/）
# 选择构建用基础镜像（选择原则：在包含所有用到的依赖前提下尽可能提及小）。如需更换，请到[dockerhub官方仓库](https://hub.docker.com/_/java?tab=tags)自行选择后替换。



#该文件是微信云托管服务的核心文件，这就是docker的配置文件，用于使用docker部署springboot


#使用3.6版本的maven jdk 8 进行构建
FROM maven:3.6.0-jdk-8-slim as build


#WORKDIR/app 就是说 指定构建过程中的工作目录   指定的工作目录是/app
WORKDIR /app

#将src目录下所有文件，拷贝到app/src目录下 app是工作目录
#src一般是存储springboot的Controller之类的文件的
COPY src /app/src

# 将pom.xml文件，拷贝到app(工作目录)下
# pom.xml文件一般是maven依赖
COPY pom.xml /app

# 执行代码编译命令
#打包

#mvn -f /app/pom.xml
#mvn  -f，-file <arg>强制使用备用POM文件（或带有pom.xml的目录），pom文件路径必须紧跟在 -f 参数后面

#mvn clean 删除已有target目录
#mvn package 使用maven进行打包
RUN mvn -f /app/pom.xml clean package

#上面的操作都在打包

#下面的操作都在配置Alpine Linux操作系统


# 选择运行时基础镜像
#Alpine Linux是一个完整的操作系统
FROM alpine:3.13

#配置sql

ENV MYSQL_HOST 10.0.224.2
ENV MYSQL_USERNAME root
ENV MYSQL_PASSWORD key,moling.6
ENV DATABASE_NAME jk-music


# 安装依赖包，如需其他依赖包，请到alpine依赖包管理(https://pkgs.alpinelinux.org/packages?name=php8*imagick*&branch=v3.13)查找。
# 选用国内镜像源以提高下载速度
#将alpine的下载源替换国内腾讯下载镜像源
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tencent.com/g' /etc/apk/repositories \
 #安装jdk8
    && apk add --update --no-cache openjdk8-jre-base \
 #然后删除缓存中的apk文件
    && rm -f /var/cache/apk/*


#WORKDIR/app 就是说 指定构建过程中的工作目录   指定的工作目录是/app
WORKDIR /app

# 将构建产物jar包拷贝到运行时目录中
#--from=build选择Alpine Linux里面的文件，因为Alpine Linux是更里面的镜像
COPY --from=build /app/target/jk-music-0.0.1.jar .

# 暴露端口
EXPOSE 9090

# 执行启动命令
CMD ["java", "-jar", "/app/jk-music-0.0.1.jar"]

