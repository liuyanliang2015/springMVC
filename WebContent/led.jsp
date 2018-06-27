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
				<label for="wearher">wearher：</label><input type="text" name="wearher" id="wearher"  />
			</div>
			
			<div class="item">
				<label for="stock">stock：</label><input type="text" name="stock" id="stock" />
			</div>
		</div>
		<script type="text/javascript" src="jquery-2.1.0.js" ></script>
		<script>
		
		
		function getWeather(){
			$.ajax({
				type:"get",
				dataType:"json",
				url:"led/weather.do",
				data:{
					//nonce_str: $("#nonce_str").val(),
					//timestamp:$("#timestamp").val(),
					//sign:$("#sign").val()
				},
				headers:{
					//token:localStorage.getItem("token")
					//将token放到请求头中
				},
				success:function(resp){
					//console.log("success:"+JSON.stringify(resp));
				    $('#wearher').val(resp.code);
				},
				error:function(resp){
					console.log("error:"+resp);
					$('#wearher').val(JSON.stringify(resp));
				}
			});
			
		}
		
		
		
		function getStock(){
			$.ajax({
				type:"get",
				dataType:"json",
				url:"led/stock.do",
				data:{
					//nonce_str: $("#nonce_str").val(),
					//timestamp:$("#timestamp").val(),
					//sign:$("#sign").val()
				},
				headers:{
					//token:localStorage.getItem("token")
					//将token放到请求头中
				},
				success:function(resp){
					//console.log("success:"+JSON.stringify(resp));
				    $('#stock').val(resp.code);
				},
				error:function(resp){
					console.log("error:"+resp);
					$('#stock').val(JSON.stringify(resp));
				}
			});
			
		}
			
			
			setInterval(function(){
				getWeather()                    
			},10000);
			
			setInterval(function(){
				getStock()                    
			},5000);
			
			
		</script>
</body>
</html>