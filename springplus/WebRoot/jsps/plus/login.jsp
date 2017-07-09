<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<link rel="icon" href="<%=basePath%>resource/favicon.ico" mce_href="<%=basePath%>resource/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" href="<%=basePath%>resource/favicon.ico" mce_href="<%=basePath%>resource/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring MVC Plus</title>
    <!-- Loading Bootstrap -->
    <link href="resource/Flat-UI-master/dist/css/vendor/bootstrap.min.css" rel="stylesheet">
    <!-- Loading Flat UI -->
    <link href="resource/Flat-UI-master/dist/css/flat-ui.min.css" rel="stylesheet">
	<script type="text/javascript" src="resource/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="resource/js/base.js"></script>
<style type="text/css">
	html,body{
		width: 100%;height: 100%;margin: 0px;padding: 0px;
	}
	.ymiddle {
	    display: -webkit-box;
	    display: -ms-flexbox;
	    display: flex;
	    -webkit-box-orient: horizontal;
	    -webkit-box-align: center;
	    -ms-flex-align:center;/* IE 10 */
	    align-items: center;
	}
	.xcenter {
		display: -ms-flexbox;
	    display: flex;
	    display: -webkit-box;
	    -webkit-box-orient: horizontal;
	    -webkit-box-pack: center;
	    box-pack: center;
	    -ms-flex-pack:center;/* IE 10 */
	    justify-content: center;/* IE 11+,Firefox 22+,Chrome 29+,Opera 12.1*/
	}
</style>
<script type="text/javascript">
	$(function(){
		if(navigator.userAgent.indexOf("MSIE")>0){   
		      if(navigator.userAgent.indexOf("MSIE 6.0")>0){   
		        alert("您使用的是ie6浏览器，此系统必须使用ie9以上浏览器。推荐使用非ie浏览器，例如Google浏览器！");    
		      }   
		      if(navigator.userAgent.indexOf("MSIE 7.0")>0){  
		        alert("您使用的是ie7浏览器，此系统必须使用ie9以上浏览器。推荐使用非ie浏览器，例如Google浏览器！");   
		      }   
		      if(navigator.userAgent.indexOf("MSIE 8.0")>0 && !window.innerWidth){//这里是重点，你懂的
		        alert("您使用的是ie8浏览器，此系统必须使用ie9以上浏览器。推荐使用非ie浏览器，例如Google浏览器！");  
		      }   
		      if(navigator.userAgent.indexOf("MSIE 9.0")>0 && !window.innerWidth){//这里是重点，你懂的
			        alert("您使用的是ie8浏览器，此系统必须使用ie9以上浏览器。推荐使用非ie浏览器，例如Google浏览器！");  
			  }   
		} 
		var username=getCookie("username");
		var password=getCookie("password");
		if(username != null){
			$("input[name='username']").val(username);
			$("input[name='password']").val(password);
			$("input[name='Verification_Code']").focus();
		}else{
			$("input[name='username']").focus();
		}
		$("input[name='Verification_Code']").keyup(function(event){
			  if(event.keyCode ==13){
				  login();
			  }
		});
	});
	function login(){
		var formObj=checkForm("form");
		formObj["log"]="登陆系统";
		post("user/loginpc",formObj,function(data){
			var errorCode=data["errorCode"];
			var errorMessage=data["errorMessage"];
			if(errorCode==1){
				alert(errorMessage);
			}else{
				data=data["data"];
				setCookie("username",data["tel"]);
				setCookie("password",data["password"]);
				setCookie("user",JSON.stringify(data));
				window.location.href="<%= request.getContextPath() %>/main";
			}
		},"json");
	}
	function updateImg(obj) { 
		obj.src = "springmvcplus/Verification_Code_image"; 
	}
</script>
</head>
<body class="ymiddle xcenter">
	<form id="form" action="login" method="post">
	 <div class="login" style="width: 940px;height: 778px;margin: 0 auto;" >
	 	<span style="position: absolute;margin-left: 410px; margin-top: 40px;font-size: 30px;" >保险智库</span>
        <div class="login-screen">
        
          <div class="login-icon">
            <img src="resource/Flat-UI-master/img/login/icon.png" alt="Welcome to Mail App" />
            <h4>version<small>1.00 Final</small></h4> 
          </div>

          <div class="login-form">
            <div class="form-group">
              <input type="text" id="username" name="username" class="form-control login-field" value="" placeholder="请输入电话" title="电话" />
              <label class="login-field-icon fui-user" for="login-name"></label>
            </div>

            <div class="form-group">
              <input type="password" id="password" name="password" class="form-control login-field" value="" placeholder="请输入密码" title="密码" />
              <label class="login-field-icon fui-lock" for="login-pass"></label>
            </div>
			<div class="form-group">
              <input type="text" id="Verification_Code" name="Verification_Code" class="form-control login-field" value="" placeholder="请输入验证码" title="验证码" />
              <label class="login-field-icon"><img id="Verification_Code_image" alt="" style="height: 38px;margin-top: -5px;" onclick="updateImg(this)" src="springmvcplus/Verification_Code_image"></label>
            </div>
            <button class="btn btn-primary btn-lg btn-block" type="button" onclick="login()">登&nbsp;录</button>
            <a class="login-link">任何疑问请登录App联系在线客服</a>
          </div>
        </div>
      </div>
	</form>
</body>
</html>