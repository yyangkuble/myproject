<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@include file="plus/js_css.jsp" %>
	<style type="text/css">
		body{
			background-color: #FFFBE8;
		}
		.titlespan{
			cursor: pointer;
		}
		.titlespan:hover{
			border-bottom:1px #8D9095 solid;
		}
		.nav{
			padding-left: 15px;padding-right: 15px;cursor: pointer;line-height: 33px;border: #FFFBE8 1px solid;border-radius:4px;margin-right: 20px;
		}
		.navative{
			padding-left: 15px;padding-right: 15px;cursor: pointer;line-height: 33px;border: #FB6F18 1px solid;border-radius:4px;margin-right: 20px;
			background-color: #FB6F18;color: white;
		}
		.nav:hover{
			border: #FB6F18 1px solid; 
		}
	</style>
  </head>
  
  <body>
    <div class="ymiddle" style="border-bottom: #C3C2C3 solid 1px;height: 45px;background-color: #F3E9C6;">
    	<div style="width: 1300px;margin: 0 auto;">
    		<span style="padding-right: 10px;margin-right: 8px;border-right: #8D9095 1px solid;"><span class="titlespan" style="color: #8D9095;"><i class="fa fa-comments"></i><span style="margin-left: 5px;color: #8D9095;">公众号</span></span></span>
    		<span style="padding-right: 10px;margin-right: 8px;border-right: #8D9095 1px solid;"><span class="titlespan" style="color: #8D9095;"><i class="fa fa-user"></i><span style="margin-left: 5px;color: #8D9095;">注册</span></span></span>
    		<span style="padding-right: 10px;margin-right: 8px;"><span style="color: #8D9095;"><i class="fa fa-bullhorn"></i><span style="margin-left: 5px;color: #8D9095;font-size: 14px;">各位攻城狮们福利来啦，基于Spring MVC最简单、最强悍的免费框架来啦，还有视频教程哦。它就是Spring MVC Plus,10分钟让你学会使用!</span></span></span>
    	</div>
    </div>
    <div class="xcenter ymiddle" style="height: 100px;">
    	<div class="ub" style="width: 1300px;">
    		<div class="ub-f1" style="font-size: 30px;">Spring MVC PLUS</div>
    		<div class="navative">首页</div>
    		<div class="nav">在线文档</div>
    		<div class="nav">视频教程</div>
    		<div class="nav">社区论坛</div>
    		<div class="nav">许可授权</div>
    		<div class="nav">联系我们</div>
    	</div>
    </div>
    sdfsdfsdfdsgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg
  </body>
</html>
