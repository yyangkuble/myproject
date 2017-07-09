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
			height: 100%;width: 100%;
		}
		.imgall{
			background-repeat: no-repeat;background-size:100% 100%;
		}
	</style>
  </head>
  
  <body>
   	<div class="ub ub-ver">
   		<div class="ub-f1 imgall" style="height:600px ;background-image: url('${share.imgUrls}'); ">
   			
   		</div>
   		<div class="xcenter" style="margin-top: -30px; z-index: 10000;position: absolute;padding-left: calc(50% - 30px)">
   			<img id="voiceImg" src="resource/imgs/baoxian/play_03.png" style="height: 60px;" onclick="play()">
   			<span style="position: absolute;margin-top: -45px;margin-left: 37px;color: white;">${share.voiceTime}</span>
   		</div>
   	</div>
   	<audio id="myaudio" src="${share.voiceUrl }" controls="controls" hidden="true"  ></audio>
   	<script type="text/javascript" src="resource/js/hide-address-bar.js"></script>
   	<script type="text/javascript" src="resource/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
		var imgindex=3;
		function play(){
			var myAuto = document.getElementById('myaudio');
			var timer = setInterval(function(){
				imgindex++;
				if(imgindex==4){
					imgindex=1;
				}
				$("#voiceImg").attr("src","resource/imgs/baoxian/play_0"+imgindex+".png");
			}, 200);
			myAuto.onended = function() {
				alert("dd");
				clearInterval(timer);
				imgindex=3;
				$("#voiceImg").attr("src","resource/imgs/baoxian/play_0"+imgindex+".png");
			};
			myAuto.play();
		}
		function stop(){
			var myAuto = document.getElementById('myaudio');
			myAuto.pause();
		}
	</script>
  </body>
</html>
