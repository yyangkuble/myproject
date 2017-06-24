<%@page import="www.springmvcplus.com.util.SpringBeanUtil"%>
<%@page import="www.springmvcplus.com.common.SpringMVCPlusArgsConfig"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String projectPath = request.getContextPath();
String rootPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+projectPath+"/";
SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
%>
<link rel="icon" href="<%=rootPath%>resource/favicon.ico" mce_href="<%=rootPath%>resource/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="<%=rootPath%>resource/favicon.ico" mce_href="<%=rootPath%>resource/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="<%=rootPath%>resource/js/base.css">
<link rel="stylesheet" type="text/css" href="<%=rootPath%>resource/dhtmlx/dhtmlxLayout_v50/codebase/dhtmlxlayout.css">
<link rel="stylesheet" type="text/css" href="<%=rootPath%>resource/fonts/font-awesome.min.css">
<script type="text/javascript" src="<%=rootPath%>resource/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=rootPath%>resource/dhtmlx/dhtmlxLayout_v50/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="<%=rootPath%>resource/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=rootPath%>resource/js/uuid.js"></script>
<script type="text/javascript" src="<%=rootPath%>resource/js/base.js"></script>
<script type="text/javascript">
	var websocket;
	var fileRootUrl="<%=springMVCPlusArgsConfig.getFileRootUrl().equals("")?rootPath+"resource/files/":springMVCPlusArgsConfig.getFileRootUrl()%>";
	function initwebsocket(){
		var username = getCookie("username");
		if ('WebSocket' in window) {
	        websocket = new WebSocket("ws://<%= rootPath.replace("http://", "") %>websocket/"+username);
	    } else if ('MozWebSocket' in window) {
	        websocket = new MozWebSocket("ws://<%= rootPath.replace("http://", "") %>websocket/"+username);
	    } else {
	        alert("您的浏览器不支持消息,请更换高版本主流浏览器");
	    }
	    websocket.onopen = function (evnt) {
	    };
	    websocket.onmessage = function (evnt) {
	        onNewMessage(evnt.data);
	    };
	    websocket.onerror = function (evnt) {
	    };
	    websocket.onclose = function (evnt) {
	    };
	}
	function sendmessage(username,message){
		websocket.send(username+"__"+message);
	}
	function sendAllUserMessage(message){
		websocket.send(message);
	}
</script>