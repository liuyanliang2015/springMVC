<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>大屏数据</title>
<!-- 路径从web.xml中配置,具体文件不需要特意引入真实的js，项目自动生成 -->
<!-- DwrDemo自动生成,名称对应dwr中配置的create:javascript,路径对应web.xml中配置的url-pattern -->
<script type="text/javascript" src='<%=request.getContextPath()  %>/dwr/engine.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()  %>/dwr/util.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()  %>/dwr/interface/MessagePusher.js'></script>
<script type="text/javascript" src='<%=request.getContextPath() %>/js/jquery.js'></script>
<script type="text/javascript">
	function init(){
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setNotifyServerOnPageUnload(true);
		MessagePusher.onPageLoad(1);
		
	}
	//页面加载时调用init函数
	window.onload = init;
	//对应appendCall设置的方法名称
	function showMessage(msg) {
		if(msg.indexOf("天气") != -1){
			$("#weather").val(msg);
		}
		if(msg.indexOf("深证") != -1){
			$("#shen_stock").val(msg);
		}
		
		if(msg.indexOf("上证") != -1){
			$("#shang_stock").val(msg);
		}
		
		if(msg.indexOf("恒生") != -1){
			$("#heng_stock").val(msg);
		}
		
		if(msg.indexOf("道琼斯") != -1){
			$("#dao_stock").val(msg);
		}
		
		if(msg.indexOf("纳斯达克") != -1){
			$("#na_stock").val(msg);
		}
		
		if(msg.indexOf("人民币") != -1){
			$("#money").val(msg);
		}
	}
</script>
</head>
<body>
	<%-- <span>当前用户id: ${param.userId }</span></br> --%>
	<textarea id="weather" rows="70" cols="20"></textarea>
	
	<textarea id="shen_stock" rows="70" cols="20"></textarea>
	
	<textarea id="shang_stock" rows="70" cols="20"></textarea>
	
	<textarea id="heng_stock" rows="70" cols="20"></textarea>
	
	<textarea id="dao_stock" rows="70" cols="20"></textarea>
	
	<textarea id="na_stock" rows="70" cols="20"></textarea>
	
	<textarea id="money" rows="70" cols="20"></textarea>
</body>
</html>