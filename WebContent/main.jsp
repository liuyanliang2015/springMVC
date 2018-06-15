<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta name="viewport" content="initial-scale=1, maximum-scale=1">
		<title>登陆</title>
</head>
	<style>
		.content{
			font-size: 14px;
			margin: 0;
			padding: 10px;
			text-align: center;
		}
		.item{
			margin: 10px;
		}
		.item input{
			width: 200px;
		}
	</style>
<body>
		<button type="button" id="getdata">getdata</button><br/>
		<button type="button" id="loginout">loginout</button><br/>
		<script type="text/javascript" src="jquery-2.1.0.js" ></script>
		<script>
			$("#getdata").on('click',function(e){
				$.ajax({
					type:"get",
					dataType:"json",
					url:"test/getData.do",
					headers:{
						token:localStorage.getItem("token")//将token放到请求头中
					},
					success:function(resp){
						if(resp.status == 0){
							$('body').append(JSON.stringify(resp.data));
						}
					}
				});
			});
			
				$("#loginout").on("click",function(){
					localStorage.removeItem("token");
					location.href="login.jsp";
				});
		</script>
</body>
</html>