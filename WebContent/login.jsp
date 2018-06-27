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
   <div class="content">
			<div class="item">
				<label for="userName">用户名</label><input type="text" name="userName" id="userName"  value="admin" />
			</div>
			<div class="item">
				<label for="password">密码：</label><input type="password" name="password" id="password"  value="123" />
			</div>
			<div class="item">
				<button type="submit" id="login">登陆</button>
			</div>
		</div>
		<script type="text/javascript" src="jquery-2.1.0.js" ></script>
		<script>
			$('#login').on('click',function (e) {
				var userName=$.trim($('#userName').val());
				var password=$.trim($('#password').val());
				$.ajax({
					type:"post",
					url:"login/login.do",
					data:{
						userName:userName,
						password:password
					},
					dataType:'json',
					success:function (resp) {
						if(resp.status == 0){
							//将token存在本地存储，然后跳转到主页面
							localStorage.setItem('token',resp.token);
							//location.href="main.jsp";
							location.href="led.jsp";
						}
					}
				});
				
				
			})
			
		</script>
</body>
</html>