# SpringMVC4 +Spring4 +Mybatis3
## 项目地址：https://github.com/liuyanliang2015/springMVC.git
## 项目介绍
本项目以初学者的角度，从0开始搭建SpringMVC框架。项目中，小编做了详细的注释，请注意remark标注。<br>
项目中用spring集成了mybatis、redis、事务、EhCache，DWR，Quartz等，满足了一般开发的需求。
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
spring-messaging-4.3.16.RELEASE.jar1
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

### 10:spring集成Ecache
缓存应用场景：<br>
常用的一些基础数据，实时性要求不高，例如区域数据 。项目启动的时候将这些数据缓存到cache，用的时候直接获取
```
ehcache-1.2.3.jar
spring-context-support-4.3.16.RELEASE.jar

```
spring配置文件中配置ecache:

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

配置ehcache.xml:
```
<?xml version="1.0" encoding="UTF-8"?>  
<ehcache>
<!-- 磁盘缓存位置 -->
<diskStore path="java.io.tmpdir"/>

<defaultCache maxElementsInMemory="10000"  memoryStoreEvictionPolicy="LRU" 
   eternal="false" timeToIdleSeconds="300" timeToLiveSeconds="300"  overflowToDisk="false" diskPersistent="false" />

<cache name="userDataCache" maxElementsInMemory="4000" eternal="true"
   overflowToDisk="false" diskPersistent="false" memoryStoreEvictionPolicy="LRU"/>    
</ehcache>
```

测试Ecache
```
/springMVC/test/com/test/CacheTest.java
```

### 11:配置视图解析器viewResolver，可以访问WEB-INF下面的页面
```
	bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/page/" />
		<property name="suffix" value=".jsp" />
		<property name="order" value="1" />
	</bean>
```
Controller用的用法：
```
@RequestMapping(value="/testView2.do",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView testView2(HttpServletRequest request){
		logger.info("call /test/testView2.do!");
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("name", "lyl");
		return new ModelAndView("/main", model);
	}
```

### 12:切面编程
配置aop.xml: <br>
```
<!--AOP用法1：代码拦截器控制 -->     
<aop:config>
   <aop:aspect id="taskAspect" ref="testTaskInterceptor">
   <!-- 多个表达式之间用||分开 -->
   <aop:pointcut id="taskPointcut" expression="(execution(* com.bert.controller.*Controller.*(..)))"/>
   <aop:after-throwing pointcut-ref="taskPointcut" method="doThrowing" throwing="ex"/>
   <aop:after-returning pointcut-ref="taskPointcut" method="doReturning" returning="result"/>
   </aop:aspect>
</aop:config>
		
		
<bean id="testTaskInterceptor" class="com.bert.task.interceptor.TaskInterceptor">
	<property name="processors">
		<list>
			<ref bean="testTaskProcessor"/>
		</list>
	</property>		
</bean>

		
<bean id="testTaskProcessor" class="com.bert.task.support.TestAOPTaskProcessor">
	<!-- 注入service -->
	<!-- Bean property 'userService' is not writable or has an invalid setter method. Does the parameter type of the setter match the return type of the getter? -->
	<!--注入的service，对应的类中必须有set方法 -->
	<property name="userService" ref="userService"/>
	<property name="interceptInfos">
		<list>
			<value>com.bert.controller.TestController:testAop</value>
		</list>
	</property>
</bean>
 
```		
测试案例：http://localhost:8080/SpringMVC/test/testAop.do <br>
访问上面的接口，就会调用TestAOPTaskProcessor中的doReturningTask方法，处理对应的业务逻辑。<br>

### 13:集成redis
redis配置文件
```
/springMVC/resource/conf/props/redis.properties
```
spring中集成redis

```

<bean id="jedisPoolConfig"  class="redis.clients.jedis.JedisPoolConfig"  >  
		    <property name="maxActive"  value="${redis.pool.maxActive}" />  
		    <property name="maxIdle"    value="${redis.pool.maxIdle}" />  
		    <property name="maxWait"    value="${redis.pool.maxWait}" />  
		    <property name="testOnBorrow" value="${redis.pool.testOnBorrow}" />  
		</bean>  
		
 <bean id="jedisShardInfo1" class="com.bert.redis.NewJedisShardInfo">  
	        <constructor-arg  index="0"   value="${redis.ip}" />  
		    <constructor-arg  index="1"   value="${redis.port}" type="int" />  
		    <constructor-arg  index="2"   value="${redis.pool.password}"/>   
 </bean>  
		

<bean id="shardedJedisPool" class="redis.clients.jedis.ShardedJedisPool" >  
		    <constructor-arg index="0"  ref="jedisPoolConfig" />  
		    <constructor-arg index="1">  
		        <list>  
		            <ref bean="jedisShardInfo1"/> 
		        </list>  
		    </constructor-arg>  
		</bean>
		
```

## 14：JWT集成

login的时候，服务器生成token

```
Map<String, Object> payload = new HashMap<String, Object>();
Date date = new Date();
payload.put("uid", "admin");// 用户ID
payload.put("iat", date.getTime());// 生成时间
payload.put("ext", date.getTime() + 1000 * 60 * 60);// 过期时间1小时
String token = Jwt.createToken(payload);
```

客户端获取到token后。保存到cookie或者localStorage。


/SpringMVC/src/com/bert/filter/TokenFilter.java

```
//拦截验证token的有效性
//从请求头中获取token
String token=request.getHeader("token");
Map<String, Object> resultMap=Jwt.validToken(token);
TokenState state = TokenState.getTokenState((String)resultMap.get("state"));
```

其他接口调用的时候，都需要带上token。服务器负责验证token是否有效。


## 15：sign签名算法

/SpringMVC/src/com/bert/common/util/SignUtil.java

接口中验签方法：
```
String sign = paramMap.get("sign");
SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
parameters.put("token", request.getHeader("token"));
parameters.put("nonce_str", paramMap.get("nonce_str"));
parameters.put("timestamp", paramMap.get("timestamp"));

boolean ifSign = SignUtil.verify2(sign, "UTF-8", parameters);
if (!ifSign) {
	result.put("status", 10001);
	result.put("msg", "sign错误");
	return result;
}
```

## 16：通用mapper集成

@Autowired
private CommonDaoMapper commonDaoMapper;

具体用法举例：
```
	Condition condition = new Condition();
    condition.addOrder(Order.desc("id"));
    condition.setFirstResult(1);
    condition.setMaxResults(10);
    List<User> list = 	commonDaoMapper.selectByCriteria(User.class,condition);
```

当然，CommonDaoMapper只支持单表查询，如果你想自定义sql，可以自定义Mapper extends CommonDaoMapper

例如：UserDaoMapper

```
	@Autowired
	private UserDaoMapper userDaoMapper;
	@Override
	public User getUser(User user) {
		return userDaoMapper.selectByPrimaryKey(User.class, user).get(0);
		//return userDao.getUser(user);
	}
	
```

## 17：集成Quartz定时任务

导入jar ：quartz-2.3.0.jar

导入/SpringMVC/resource/conf/spring/spring-quartz.xml

```

<!-- 天气定时任务begin -->
    <bean id="weatherTimingSchedule" class="com.bert.timer.WeatherTimingSchedule"/>
    <!-- 加入定时执行的方法 -->
    <bean id="weatherTimingScheduleJobDetail" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
        <!-- 定时执行的类 -->
        <property name="targetObject" ref="weatherTimingSchedule"/>
        <!-- 具体的方法 -->
        <property name="targetMethod" value="execute"/>
    </bean>
     <!-- 调度触发器，设置自己想要的时间规则 -->
    <bean id="weatherTimingScheduleTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
        <!-- 加入相关的执行类和方法 -->
        <property name="jobDetail" ref="weatherTimingScheduleJobDetail"/>
        <!-- 设置时间规则 （为了方便测试，设置成一分钟一次。具体的规则见详情）-->
        <property name="cronExpression" value="0/2 * * * * ? "/>    
    </bean>
    
 <!-- 天气定时任务end -->

<!-- 将两个定时任务加入调度工厂 ,设置调度触发器即可-->
    <bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
        <property name="triggers">
            <list>
                <ref bean="weatherTimingScheduleTrigger"/>
            </list>
        </property>
    </bean>


```

## 18：集成DWR

导入需要的jar：dwr-3.0.2-RELEASE.jar

在web.xml中配置dwr:

```

<!-- 配置DWR -->
	<servlet>
    <servlet-name>dwr-invoker</servlet-name>
    <servlet-class>org.directwebremoting.servlet.DwrServlet</servlet-class>
    <init-param>
      <param-name>debug</param-name>
      <param-value>false</param-value>
    </init-param>
    <init-param>
      <param-name>activeReverseAjaxEnabled</param-name>
      <param-value>true</param-value>
    </init-param>
    <init-param>
      <param-name>initApplicationScopeCreatorsAtStartup</param-name>
      <param-value>true</param-value>
    </init-param>
    <init-param>
      <param-name>jsonRpcEnabled</param-name>
      <param-value>true</param-value>
    </init-param>
    <init-param>
      <param-name>jsonpEnabled</param-name>
      <param-value>true</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dwr-invoker</servlet-name>
    <url-pattern>/dwr/*</url-pattern>
  </servlet-mapping>

```

在web.xml同级目录引入dwr.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE dwr PUBLIC "-//GetAhead Limited//DTD Direct Web Remoting 3.0//EN" "http://getahead.org/dwr/dwr30.dtd">

<dwr>
  <allow>
  	<create creator="new" javascript="MessagePusher"> 
         <param name="class" value="com.bert.dwr.MessagePusher"/> 
    </create> 
  </allow>
</dwr>

```

当运行

http://localhost:8080/SpringMVC/web/toLed.do?userId=1

http://localhost:8080/SpringMVC/web/toLed.do?userId=2

分别跳转到led_weather.jsp和led_stock.jsp两个页面后，

会自动生成文件/dwr/interface/MessagePusher.js

后台定时任务，执行任务：

/SpringMVC/src/com/bert/timer/StockTimingSchedule.java

/SpringMVC/src/com/bert/timer/WeatherTimingSchedule.java

```
 MessagePusher push = new MessagePusher();
 push.sendMessage("2", "股票信息:"+RandomUtil.getRandomString(16));
```

就会触发前端的showMessage()方法，将推送的数据展示在前端jsp页面。
