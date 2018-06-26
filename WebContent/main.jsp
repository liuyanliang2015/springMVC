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
				<label for="userName">nonce_str：</label><input type="text" name="nonce_str" id="nonce_str"  value="调用方随机生成字符串" />
			</div>
			<div class="item">
				<label for="password">timestamp：</label><input type="text" name="timestamp" id="timestamp"  value="调用放时间戳" />
			</div>
			<div class="item">
				<label for="password">sign：</label><input type="text" name="sign" id="sign"  value="调用方计算的签名" />
			</div>
			
			<div class="item">
				<label for="password">response：</label>
				<textarea style="width: 300px;height: 200px;" id="responseData"></textarea>
			</div>
			<div class="item">
						<button type="button" id="getdata">getdata</button><br/>
						<button type="button" id="loginout">loginout</button><br/>
			</div>
		</div>
		<script type="text/javascript" src="jquery-2.1.0.js" ></script>
		<script>
			$("#getdata").on('click',function(e){
				$.ajax({
					type:"post",
					dataType:"json",
					url:"test/queryByServiceSign.do",
					data:{
						nonce_str: $("#nonce_str").val(),
						timestamp:$("#timestamp").val(),
						sign:$("#sign").val()
					},
					headers:{
						token:localStorage.getItem("token")
						//将token放到请求头中
					},
					success:function(resp){
						console.log("success:"+JSON.stringify(resp));
					    $('#responseData').val(JSON.stringify(resp));
					},
					error:function(resp){
						console.log("error:"+resp);
						$('#responseData').val(JSON.stringify(resp));
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