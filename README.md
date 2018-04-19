# SpringMVC
## 项目地址：https://github.com/liuyanliang2015/springMVC.git
## 项目介绍
本项目以初学者的角度，从0开始搭建SpringMVC框架。项目中，小编做了详细的注释，请注意remark标注。
## 搭建过程
### 1：导入springMVC需要的核心包
```
aopalliance-1.0.jar
commons-logging-1.2.jar
spring-aop-4.3.16.RELEASE.jar
spring-aspects-4.3.16.RELEASE.jar
spring-beans-4.3.16.RELEASE.jar
spring-context-4.3.16.RELEASE.jar
spring-context-support-4.3.16.RELEASE.jar
spring-core-4.3.16.RELEASE.jar
spring-expression-4.3.16.RELEASE.jar
spring-instrument-4.3.16.RELEASE.jar
spring-instrument-tomcat-4.3.16.RELEASE.jar
spring-jdbc-4.3.16.RELEASE.jar
spring-jms-4.3.16.RELEASE.jar
spring-messaging-4.3.16.RELEASE.jar
spring-orm-4.3.16.RELEASE.jar
spring-oxm-4.3.16.RELEASE.jar
spring-test-4.3.16.RELEASE.jar
spring-tx-4.3.16.RELEASE.jar
spring-web-4.3.16.RELEASE.jar
spring-webmvc-4.3.16.RELEASE.jar
spring-webmvc-portlet-4.3.16.RELEASE.jar
spring-websocket-4.3.16.RELEASE.jar

```
### 2：配置web.xml
配置contextConfigLocation加载spring的xml
配置ContextLoaderListener
配置DispatcherServlet

### 3：配置slf4j+logback日志
依赖的jar：
```
logback-classic-1.2.3.jar
logback-core-1.2.3.jar
slf4j-api-1.7.5.jar

```
配置logback.xml

### 4：配置springMVC-jackson支持
配置的目的是,让springMVC可以返回Json数据。
依赖的jar：
```
jackson-annotations-2.5.0.jar
jackson-core-2.5.0.jar
jackson-databind-2.5.0.jar

```

### 5：编写测试接口com.bert.controller.TestController
打开浏览器访问：http://localhost:8080/SpringMVC/test/test.do </br>
如果浏览器输出：{"code":0} </br>
恭喜你，基本环境搭建完成！</br>
