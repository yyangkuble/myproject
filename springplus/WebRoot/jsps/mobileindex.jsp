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
	<link rel="stylesheet" type="text/css" href="resource/imgs/baoxian/js/swiper-3.4.2.min.css">
	<style type="text/css">
		
		html,body{
			margin: 0;padding: 0;
			height: 100%;width: 100%;
		}
		.swiper-container{
			height: 100%;
			width: 100%;
		}
		.imgall{
			background-repeat: no-repeat;background-size:100% 100%;
		}
	</style>
  </head>
  
  <body>
   		<div class="swiper-container">
		    <div class="swiper-wrapper">
		        <div class="swiper-slide imgall" style="background-image: url('resource/imgs/baoxian/www/13.png');">
		        	<%
		        		if(request.getAttribute("type").equals("ios")){
		        	%>
		        	<div class="imgall" style="background-image: url('resource/imgs/baoxian/www/17.png');width: 70%; margin-left: 15%;height: 50px;margin-top: 500px;" onclick="iosdownload()"></div>
		        	<%		
		        		}else{//andriod
        			%>
		        	<div class="imgall" style="background-image: url('resource/imgs/baoxian/www/18.png');width: 70%; margin-left: 15%;height: 50px;margin-top: 500px;" onclick="androiddownload()"></div>
		        	<%		
		        		}
		        	%>
		        </div>
		        <div class="swiper-slide imgall" style="background-image: url('resource/imgs/baoxian/www/14.png');"></div>
		        <div class="swiper-slide imgall" style="background-image: url('resource/imgs/baoxian/www/15.png');"></div>
		        <div class="swiper-slide imgall" style="background-image: url('resource/imgs/baoxian/www/16.png');">
		        	<%
		        		if(request.getAttribute("type").equals("ios")){
		        	%>
		        	<div class="imgall" style="background-image: url('resource/imgs/baoxian/www/17.png');width: 70%; margin-left: 15%;height: 50px;margin-top: 500px;" onclick="iosdownload()"></div>
		        	<%		
		        		}else{//andriod
        			%>
		        	<div class="imgall" style="background-image: url('resource/imgs/baoxian/www/18.png');width: 70%; margin-left: 15%;height: 50px;margin-top: 500px;" onclick="androiddownload()"></div>
		        	<%		
		        		}
		        	%>
		        </div>
		    </div>
		</div>
   	<script type="text/javascript" src="resource/js/hide-address-bar.js"></script>
   	<script type="text/javascript" src="resource/imgs/baoxian/js/swiper-3.4.2.min.js"></script>
	<script type="text/javascript">
	 	var mySwiper = new Swiper ('.swiper-container', {
		    loop: false
		  });
	 	
	 	var iosurl="${iosurl}";
		var androidurl="${androidurl}";
		function iosdownload(){
			window.open(iosurl, "_blank");
		}
		function androiddownload(){
			downloadfile(androidurl);
		}
	
		function downloadfile(fileurl){
			if(new RegExp("^<").test(fileurl)){
				fileurl=$(fileurl).text();
			}
			if(new RegExp("^http").test(fileurl)){
				$("body").append('<iframe style="display: none;" src="'+fileurl+'"></iframe>');
			}else{
				$("body").append('<iframe style="display: none;" src="'+fileRootUrl+fileurl+'"></iframe>');
			}
		}
	</script>
  </body>
</html>
