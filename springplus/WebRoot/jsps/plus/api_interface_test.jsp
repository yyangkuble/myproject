<%@page import="www.springmvcplus.com.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String type=StringUtil.valueOf(request.getAttribute("type"));//类型是 新增还是 修改 update是新增
String updateId=StringUtil.valueOf(request.getAttribute("id"));
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Spring MVC Plus菜单管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@include file="js_css.jsp" %>
	<link rel="stylesheet" type="text/css" href="resource/js/sql/json.css">
	<script type="text/javascript" src="resource/js/template.js"></script>
	<script type="text/javascript" src="resource/js/sql/json.js"></script>
	
	<style type="text/css">
		html,body{
			height: 100%;width: 100%;overflow: hidden;background-color: RGBA(214,235,245,0.1);
		}
	</style>
	<script type="text/javascript">
		var systemurl;
		var modename;
		var desc;
		$(function(){
			var apiId="<%= request.getAttribute("id") %>";
			post("select/Api/byid",{id:apiId},function(api){
				systemurl=api["url"];
				modename=api["modename"];
				desc=api["describle"];
				var type=api["apitype"];
				if(type=="sql"){
					if($.trim(api["url"]).indexOf("select")==0){
						systemurl="select/"+apiId+"/1";
						$("#sqldisplay").css("display","");
					}else{
						systemurl="exc/"+apiId;
					}
				}
				initglobe(JSON.parse(api["requestparam"]));
			},"json");
		});
		function initglobe(requestparam){
			$("#modename").text(modename);
			$("#apiname").text(desc);
			$("#requests").append(template("systemurl",{value:systemurl}));
			for(var key in requestparam){
				var obj=new Object();
				obj["key"]=key;
				obj["value"]=requestparam[key];
				var html=template("requesttemplate",obj);
				$("#requests").append(html);
			}
		}
		function send(){
			var requestdata=new Object();
			$("#requests .requestvalue").each(function(){
				if($.trim($(this).val())!="null"){
					requestdata[$(this).attr("name")]=$(this).val();
				}
			});
			var url = $("input[name='systemurl']").val();
			if($.trim(url).indexOf("select")==0){
				post(url,requestdata,function(data){
					Process(data,"jsondata");
				},"text");
			}else{
				post(url,requestdata,function(data){
					Process(data,"jsondata");
				},"text");
			}
			
		}
	</script>
  </head>
  
  <body>
	 <!-- 修改新增加修改 -->
	 <div id="modeldiv" class="scrollbar" style="height: 100%;width: 100%;overflow: auto;">
	 	<div style="padding-left: 10px;">
	 		<div style="margin-top: 20px;">
	 			<table>
	 				<tr>
	 					<td><span id="modename" style="font-size: 22px;"></span>：</td>
	 					<td><span id="apiname" style="font-size: 18px;color: #428BCA;"></span></td>
	 					<td><span onclick="send()" style="margin-left: 30px;" class="btn">&nbsp;测试&nbsp;</span></td>
	 					<td><span id="sqldisplay" style="font-size: 14px;color: #18528E;margin-left: 20px;display: none;">提示:可以将最后一个url参数，选择为 [1:页数,all:所有,onerow:一行,one:一行一列,excel:生成表格]</span></td>
	 				</tr>
	 			</table>
	 		</div>
	 		<table id="requests">
	 		</table>
	 		<div style="margin-top: 50px;">返回结果:</div>
	 		<div id="jsondata" style="margin-top: 20px;margin-left: 60px;"></div>
	 	</div>
	 </div>
	<script id="requesttemplate" type="text/html">
		<tr style="height: 40px;">
	 				<td valign="bottom"><span class="lable" style="margin-left: 2px;font-size: 17px;">{{key}}:</span></td>
	 				<td valign="bottom"><input name="{{key}}" value="" class="input noinput requestvalue" style="width: 485px;border-left: 0px red solid;border-right: 0px red solid;border-top: 0px red solid;border-radius:0px;background-color: transparent;"></td>
					<td valign="bottom"><span class="lable" style="margin-left: 10px;font-size: 15px;">{{value}}</span></td>	 			
		</tr>
	</script>
	<script id="systemurl" type="text/html">
		<tr style="height: 40px;">
	 				<td valign="bottom"><span class="lable" style="margin-left: 2px;font-size: 17px;color:#3399CC;">URL:</span></td>
	 				<td valign="bottom"><input name="systemurl" value="{{value}}" class="input" style="width: 485px;border-left: 0px red solid;border-right: 0px red solid;border-top: 0px red solid;border-radius:0px;background-color: transparent;"></td>
	 			</tr>
	</script>
  </body>
</html>
