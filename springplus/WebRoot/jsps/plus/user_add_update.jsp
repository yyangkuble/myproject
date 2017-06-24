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
	<style type="text/css">
		html,body{
			height: 100%;width: 100%;overflow: hidden;background-color: RGBA(214,235,245,0.1);
		}
	</style>
	<script type="text/javascript">
		var updateId="<%=updateId.trim()%>";
		$(function(){
			if(updateId != ""){//如果是修改
				$post("select/User/byid",{id:updateId},function(thedata){
					//填充数据
					fillForm("modeldiv",thedata);
				},"json");
			}
		});
		
		function addOrUpdate(element){//新增/修改sql
			var formdata=checkForm("modeldiv");
			if(formdata !=null){
				if($(element).text()=="确定新增"){
					formdata["password"]="password";
					formdata["addtime"]=dateFormat(new Date(),"yyyy-MM-dd hh:mm:ss");
					formdata["islock"]=0;
					formdata["lastsetpasswordtime"]="newuser";
					formdata["errorpasswordtimes"]=0;
					post("select/user_check_username_isused/one",{username:formdata["username"]},function(data){
						if(data==0){
							formdata["log"]="添加用户";
							post("insert/User",formdata,function(result){
								if(result==1){
									alert("用户添加成功");
									openurl("plus/userManager");
								}
							},"json");
						}else{
							alert("用户名已经存在");
						}
					},"json");
				}else{
					formdata["log"]="修改用户";
					$post("update/User",formdata,function(data2){
						if(data2==1){
							alert("修改成功");
							openurl("plus/userManager");
						}else{
							alert("修改失败");
						}
					},"json");
				}
			}
		}
	</script>
  </head>
  
  <body>
	 <!-- 修改新增加修改 -->
	 <div id="modeldiv" class="scrollbar" style="height: 100%;width: 100%;overflow: auto;">
	 	<div class="ub" style="height: 40px;background-image: url('resource/imgs/head-ico1.png');background-size:40px 40px;">
	    	<div class="ub ub-f1" style="height: 100%;line-height: 40px;padding-left: 10px;font-size: 13px;">
	    		<div id="menu1Name"><%= request.getAttribute("menu1Name") %></div>
	    		<div style="padding-left:17px;height: 100%;background-image: url(resource/imgs/head-ico.png);margin-left: 10px;margin-right: 15px;"></div>
	    		<div id="menu2Name"><%= request.getAttribute("menu2Name") %></div>
	    		<div style="padding-left:17px;height: 100%;background-image: url(resource/imgs/head-ico.png);margin-left: 10px;margin-right: 15px;"></div>
	    		<div>菜单<%= StringUtil.hashText(type)?"修改":"新增" %></div>
	    		<div style="padding-left:17px;height: 100%;background-image: url(resource/imgs/head-ico.png);margin-left: 10px;margin-right: 15px;"></div>
	    	</div>
	    	<div class="title" onclick="addOrUpdate(this)" style="width: 80px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="确定">确定<%= StringUtil.hashText(type)?"修改":"新增" %></div>
	    </div>
	 	<div>
	 		<table style="margin-left: 10px;margin-top: 20px;">
	 			<tr style="height: 40px;">
	 				<td valign="top"><input name="id" style="display: none;" type="text"><span class="lable">用户名:</span></td>
	 				<td valign="top"><input name="username" type="text" class="input" title="用户名"></td>
	 				<td valign="top"><span class="lable" style="margin-left: 20px;">姓名:</span></td>
	 				<td valign="top"><input name="name" type="text" class="input" title="姓名"></td>
	 				<td valign="top"><span class="lable" style="margin-left: 20px;">电话:</span></td>
	 				<td valign="top"><input name="tel" class="input noinput" title="电话" style="width: 300px;"></td>
	 			</tr>
	 		</table>
	 		<table style="margin-left: 10px;margin-top: 20px;">
	 			<tr style="height: 40px;">
	 				<td valign="top"><span class="lable">移动端角色:</span></td>
	 				<td valign="top">
	 				<select name="approle" class="select noinput" title="移动端角色">
	 					<option value="">无</option>
	 					<option value="service">服务站</option>
	 					<option value="engineer">工程师</option>
	 				</select>
	 				</td>
	 			</tr>
	 		</table>
	 	</div>
	 </div>
	
  </body>
  <script type="text/html">
        <div class="checkbox"><input name="testcheckbox" type="checkbox" value="角色1"></div>
		<div class="radiobox"><input type="radio" name="testradio" checked="checked" value="女" /></div>
	</script>
</html>
