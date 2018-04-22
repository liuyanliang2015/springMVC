# SpringMVC4 +Spring4 +Mybatis3
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

### 8:配置事务
Spring事务的本质其实就是数据库对事务的支持。<br>
Spring提供了两种事务管理的方式：编程式事务管理和声明式事务管理。<br>
声明式事务管理有两种常用的方式：<br>
一种是基于tx和aop命名空间的xml配置文件，<br>
一种是基于@Transactional注解<br>
随着Spring和Java的版本越来越高，大家越趋向于使用注解的方式。 <br>
```
<!-- 事务配置begin -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"/>
	</bean>

	<!-- 声明式事务方式1，基于tx和aop命名空间的xml配置文件-->
	<tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<tx:method name="add*" propagation="REQUIRED" rollback-for="Exception"/>
			<tx:method name="delete*" propagation="REQUIRED" rollback-for="Exception"/>
			<tx:method name="update*" propagation="REQUIRED" rollback-for="Exception"/>
			<tx:method name="*" read-only="true"/>
		</tx:attributes>
	</tx:advice>
	

	<!-- execution(* com.bert.core.*.*.*.*(..))  注意表达式中间用OR链接，大小写敏感-->
	<aop:config>
		<aop:pointcut id="txPointcut" expression="execution(* com.bert.core.*.*.*.*(..)) "/>
		<aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut" order="1"/>
	</aop:config>


	<!-- 声明式事务方式2，基于tx和aop命名空间的xml配置文件-->
	<tx:annotation-driven transaction-manager="transactionManager"/>
	<!--事务配置end -->
```	
	
依赖的jar：
```
aopalliance-1.0.jar
aspectjweaver-1.7.0.jar
spring-tx-4.3.16.RELEASE.jar

```

单元测试父类BaseSpringTestCase，加入事务注解
```
//全局配置，这样写不能加载具体的方法上
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)  
@Transactional  
```
具体的单元测试方法上面，加上注解
```
//标明此方法需使用事务
@Transactional
//false标明使用完此方法后事务不回滚,true时为回滚 
@Rollback(true)
```

### 9:集成mybatis
```
mybatis-3.3.0.jar
mybatis-spring-1.2.3.jar

```
在spring配置文件context.xml，集成mybatis
```
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
	  <property name="dataSource" ref="dataSource" />
	  <property name="configLocation" value="classpath:conf/mybatis/config.xml"/>
	</bean>	
	
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
       <property name="basePackage" value="com.bert.core.**.dao" />
    </bean>
```
配置mybatis配置文件config.xml
```
<configuration>
		<settings>
			<!--设置日志记录使用方式-->
			<setting name="logPrefix" value="dao."/>  
		</settings>
		<!-- 配置类型别名-->
		<typeAliases>
		  	<package name="com.bert.domain"/>
		</typeAliases>
		<!-- 翻页插件拦截器-->
		<plugins>
			<plugin interceptor="com.bert.query.MyBatisPaginationInterceptor">
				<property name="dialect" value="mysql" />
			</plugin>
		</plugins>
	</configuration>
```
注意：beans.xml中配置组件扫描器，否则无法将service注入到spring容器
```
 <context:component-scan base-package="com.bert"/>

```

### 8:spring集成Ecache
```
ehcache-1.2.3.jar
spring-context-support-4.3.16.RELEASE.jar

```
spring配置文件中配置ecache

```
<bean id="cacheManager" class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean">
		<property name="configLocation">
			<value>classpath:conf/cache/ehcache.xml</value>
		</property>
	</bean>

	<bean id="cacheFactory" class="com.bert.factory.EhCacheFactory">
		<property name="cacheManager" ref="cacheManager"/>
	</bean>
```

配置ehcache.xml
```
<?xml version="1.0" encoding="UTF-8"?>  
<ehcache>
	<!-- 磁盘缓存位置 -->
 	<diskStore path="java.io.tmpdir"/>
   
	    <defaultCache 
	    maxElementsInMemory="10000" 
	    memoryStoreEvictionPolicy="LRU" 
	    eternal="false"
		timeToIdleSeconds="300" 
		timeToLiveSeconds="300" 
		overflowToDisk="false" 
		diskPersistent="false" />
        
	    <cache name="userDataCache"
	       maxElementsInMemory="4000"
	       eternal="true"
	       overflowToDisk="false"
	       diskPersistent="false"
	       memoryStoreEvictionPolicy="LRU"/>    
</ehcache>
```

测试Ecache
```
/springMVC/test/com/test/CacheTest.java
```
