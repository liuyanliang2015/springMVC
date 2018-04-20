# SpringMVC +Spring +Mybatis
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
配置contextConfigLocation加载spring的xml </br>
配置ContextLoaderListener </br>
配置DispatcherServlet </br>

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


### 6:配置jdbc
(1)加入/springMVC/resource/conf/props/jdbc.properties </br>
(2)context.xml中配置dataSource数据源和JdbcTemplate </br>
(3)加入依赖的jar  </br>
```
commons-dbcp-1.4.jar
commons-pool-1.5.6.jar
mysql-connector-java-5.1.7-bin.jar

```
(4)context.xml配置context:property-placeholder，否则无法获取props文件 </br>
```
<context:property-placeholder location="classpath:conf/props/**/*.properties"/>
```
(5)访问测试接口：http://localhost:8080/SpringMVC/test/queryUser.do </br>
如果返回{"张三":11,"李四":12}，则jdbc配置ok! </br>

### 7:配置单元测试
spring4在单元测试的时候，需要junit4.12版本以上。
而junit4.12，又依赖hamcrest-core-1.3.jar
```
junit-4.12.jar
hamcrest-core-1.3.jar

```

```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:spring_test.xml"
})
public class BaseSpringTestCase {

}

public class UserRoleDealTest extends BaseSpringTestCase {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("rawtypes")
	@org.junit.Test
	public void testData() throws Exception {
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from tb_user");
			for (Map row : rows) {
				System.out.println(row.get("NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

```

