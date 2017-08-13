<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
    <meta name="viewport" content="width=device-width,maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="resource/js/base.css">
	<link rel="stylesheet" type="text/css" href="resource/fonts/font-awesome.min.css">
	<style type="text/css">
		
		html,body{
			margin: 0;padding: 0;
			height: 666px;width: 100%;
			background-color: #FBCA04;
		}
		.imgall{
			background-repeat: no-repeat;background-size:100% 100%;
		}
	</style>
  </head>
  
  <body>
   	<div class="ub ub-ver" style="height: calc(100% - 40px);">
   		<div class="ub-f1 imgall" style="background-image: url('resource/imgs/baoxian/09.png');">
   			<div class="imgall" style="height:100px;background-image: url('resource/imgs/baoxian/01.png');width: calc(100% - 26px);margin-left: 5px;">
   				<div style="height: 34px;"></div>
   				<div class="ub">
   					<div>
   						<img src="${user.imgUrl }" style="width: 52px; height: 52px;border-radius:50%;margin-left: 35px;">
   					</div>
   					<div style="height: 52px;padding-left: 10px;font-size: 1.1em;" class="ymiddle">${ user.name }: 让我们一起加入保险智库吧！ </div>
   				</div>
   			</div>
   			<div class="imgall" style="height:130px;background-image: url('resource/imgs/baoxian/08.png');width: calc(100% - 40px);margin-left: 20px;margin-top: 30px;">
   			</div>
   			<div class="imgall" style="height:55px;background-image: url('resource/imgs/baoxian/02.png');width: 50%;margin-left: 25%;margin-top: 20px;">
   			</div>
   			<div class="imgall ymiddle" style="height:60px;background-image: url('resource/imgs/baoxian/03.png');width: calc(100% - 60px);margin-left: 30px;margin-top: 130px;">
   				<input id="tel" style="height: 40px;width: calc(100% - 60px);margin-left: 50px;font-size: 1.2em;border:0px;background:rgba(0, 0, 0, 0);" placeholder="请输入手机号">
   			</div>
   			<div onclick="zhuche()" class="imgall" style="height:60px;background-image: url('resource/imgs/baoxian/04.png');width: calc(100% - 60px);margin-left: 30px;margin-top: 20px;">
   			</div>
   			<div style="width: calc(100% - 60px);margin-left: 30px;margin-top: 10px;color: #F24050;font-size: 0.9em;">
   				*需注册为付费会员,才可领取50元现金,并获得邀请资格
   			</div>
   		</div>
   	</div>
   	
   	<div class="mengban" style="height: 666px; width: 100%;position: absolute;left: 0;top: 0;z-index: 10;background-color: RGBA(0,0,0,0.5);display: none;"></div>
   	<div class="mengban" style="height: 666px; width: 100%;position: absolute;left: 0;top: 0;z-index: 11;display: none;">
   		<div class="imgall" style="height:290px;background-image: url('resource/imgs/baoxian/05.png');width: calc(100% - 80px);margin-left: 40px;margin-top: 180px;">
   			<div style="height: 110px;"></div>
   			<div style="padding-left: 40px;padding-right: 40px;">
   				<span>您已经接受${user.name }的邀请，马上下载保险智库APP开通会员，即可获得现金奖励</span>
   			</div>
   			<div><img src="resource/imgs/baoxian/07.png" style="width: 60%; margin-left: 20%;margin-top: 30px;" onclick="queren()"></div>
   		</div>
   	</div>
   	<script type="text/javascript" src="resource/js/hide-address-bar.js"></script>
   	<script type="text/javascript" src="resource/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
		function zhuche(){
			var tel=$.trim($("#tel").val());
			if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(tel)) || tel.length != 11){ 
		        alert("您输入的手机号有误!"); 
		        return false; 
		    }else{
		    	$.post("user/askFriend",{friendTel:tel,userId:"${user.id}"},function(data){
		    		if(data["errorCode"]==0){
		    			$(".mengban").css("display","");
		    		}else{
		    			alert(data["errorMessage"]);
		    		}
		    	},"json");
		    }
		}
		function queren(){
			$(".mengban").css("display","none");
		}
	</script>
  </body>
</html>
