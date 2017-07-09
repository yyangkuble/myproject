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
		.center{
			width: 1300px;margin: 0 auto;
		}
	</style>
  </head>
  
  <body>
   	<div>
   		<div class="imgall" style="background-image:url('resource/imgs/baoxian/www/19.jpg') ;height: 700px;">
   			<div style="height: 70px;">
   				<div class="center ub " style="height: 100%;">
   					<div class="ymiddle" style="height: 100%;">
   						<img src="resource/imgs/baoxian/www/22.png" style="height: 25px;">
   					</div>
   					<div class="ub-f1"></div>
   					<div class="ymiddle" style="height: 100%;">
   						<img alt="" src="resource/imgs/baoxian/www/21.png"><span style="cursor: pointer;color: white;margin-left: 8px;">登录</span>
   					</div>
   				</div>
   			</div>
   			<div style="background-color:RGBA(255,255,255,0.1);height: 2px;"></div>
   			<div class="center">
   				<div style="margin-top: 100px;">
	   				<img alt="" src="resource/imgs/baoxian/www/1.png" style="height: 110px;">
   				</div>
   				<div class="ub" style="margin-top: 34px;">
   					<div class="ub-f1">
   						<div class="ub" style="margin-top: 80px;">
   							<div><img alt="" src="resource/imgs/baoxian/www/03.png" style="height: 50px;"></div>
   							<div style="margin-left: 50px;"><img alt="" src="resource/imgs/baoxian/www/02.png" style="height: 50px;"></div>
   						</div>
   						<div style="margin-top: 50px;">
   							<img alt="" src="resource/imgs/baoxian/www/erweima.png" style="border-radius:5px;margin-left: 165px;">
   						</div>
   					</div>
   					<div style="padding-left: 100px;" class="xcenter"><img alt="" src="resource/imgs/baoxian/www/04.png" style="height: 380px;"></div>
   				</div>
   			</div>
   		</div>
   		
   		<div style="margin-top: 70px;padding-bottom: 80px;">
   			<div class="center">
   				<div class="ub" style="z-index: 11;">
   					<div><img src="resource/imgs/baoxian/www/05.png"></div>
   					<div class="ub-f1" style="padding-left: 25px;">
   						<div style="margin-top: 20px;font-size: 25px;font-weight: 900;">最便利的客户管理系统</div>
   						<div style="margin-top: 10px;color: RGBA(0,0,0,0.8)" >客户升级系统  智慧贴心提示</div>
   					</div>
   					<div>
   						<img alt="" src="resource/imgs/baoxian/www/06.png">
   					</div>
   				</div>
   				<div class="ub" style="margin-top: -50px;z-index: 10">
   					<div>
   						<img alt="" src="resource/imgs/baoxian/www/08.png">
   					</div>
   					<div class="ub-f1 ub" style="padding-right: 25px;">
   						<div class="ub-f1"></div>
   						<div>
	   						<div style="margin-top: 160px;font-size: 25px;font-weight: 900;">最便利的客户管理系统</div>
	   						<div style="margin-top: 10px;color: RGBA(0,0,0,0.8);" >客户升级系统  智慧贴心提示</div>
   						</div>
   					</div>
   					<div><img src="resource/imgs/baoxian/www/07.png" style="margin-top: 140px;"></div>
   				</div>
   				
   				<div class="ub" style="z-index: 11;margin-top: 70px;">
   					<div><img src="resource/imgs/baoxian/www/09.png"></div>
   					<div class="ub-f1" style="padding-left: 25px;">
   						<div style="margin-top: 20px;font-size: 25px;font-weight: 900;">最有效的增员利器</div>
   						<div style="margin-top: 10px;color: RGBA(0,0,0,0.8)" >精准的八大特质分析</div>
   						<div style="margin-top: 10px;color: RGBA(0,0,0,0.8)" >选拔人才知人善用</div>
   					</div>
   					<div>
   						<img alt="" src="resource/imgs/baoxian/www/10.png">
   					</div>
   				</div>
   				<div class="ub" style="margin-top: -50px;z-index: 10">
   					<div>
   						<img alt="" src="resource/imgs/baoxian/www/12.png">
   					</div>
   					<div class="ub-f1 ub" style="padding-right: 25px;">
   						<div class="ub-f1"></div>
   						<div>
	   						<div style="margin-top: 160px;font-size: 25px;font-weight: 900;">最夯实的团队经营</div>
	   						<div style="margin-top: 10px;color: RGBA(0,0,0,0.8);" >贴心活动案例  温馨文化塑造</div>
   						</div>
   					</div>
   					<div><img src="resource/imgs/baoxian/www/11.png" style="margin-top: 140px;"></div>
   				</div>
   			</div>
   		</div>
   	</div>
   	<script type="text/javascript" src="resource/js/hide-address-bar.js"></script>
   	<script type="text/javascript" src="resource/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
		
	</script>
  </body>
</html>
