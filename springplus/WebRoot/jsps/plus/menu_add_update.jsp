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
				$post("select/Menu/byid",{id:updateId},function(thedata){
					//填充菜单类别parentMenus
					var parentid=thedata["parentid"];
					if(parentid != "0"){//如果修改的不是主菜单
						post("select/menu_by_parentid/1",{parentid:'0'},function(data){
							var list=data["list"];
							for(var i=0;i<list.length;i++){
								var thedata=list[i];
								var html='<option value="'+thedata["id"]+'">'+thedata["name"]+'</option>';
								$("#parentMenus").append(html);
							}
						},"json");
					}else{
						var html='<option value="0">主菜单</option>';
						$("#parentMenus").append(html);
					}
					//填充数据
					fillForm("modeldiv",thedata);
				},"json");
			}else{
				var html='<option value="0">主菜单</option>';
				$("#parentMenus").append(html);
				$post("select/menu_by_parentid/1",{parentid:'0'},function(data){
					var list=data["list"];
					for(var i=0;i<list.length;i++){
						var thedata=list[i];
						html='<option value="'+thedata["id"]+'">'+thedata["name"]+'</option>';
						$("#parentMenus").append(html);
					}
				},"json");
			}
		});
		function editsql(element){//新增/修改sql
			var formdata=checkForm("modeldiv");
			if(formdata !=null){
				if($(element).text()=="确定新增"){
					post("select/menu_get_max_order_code_by_parentid/one",{parentid:formdata["parentid"]},function(data){
						formdata["orderby"]=data;
					},"json");
					$post("insert/Menu",formdata,function(data2){
						if(data2==1){
							alert("添加成功");
							openurl("plus/meunManager");
						}else{
							alert("添加失败");
						}
					},"json");
				}else{
					$post("update/Menu",formdata,function(data2){
						if(data2==1){
							alert("修改成功");
							openurl("plus/meunManager");
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
	    	<div class="title" id="editsql" onclick="editsql(this)" style="width: 80px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="确定">确定<%= StringUtil.hashText(type)?"修改":"新增" %></div>
	    </div>
	 	<div>
	 		<table style="margin-left: 10px;margin-top: 20px;">
	 			<tr style="height: 40px;">
	 				<td valign="top"><input name="id" style="display: none;" type="text"><span class="lable">菜单名称:</span></td>
	 				<td valign="top"><input name="name" type="text" class="input" title="菜单名称"></td>
	 				<td valign="top"><span class="lable" style="margin-left: 20px;">所属菜单:</span></td>
	 				<td valign="top">
		 				<select id="parentMenus" name="parentid" class="select" title="所属菜单">
		 				</select>
	 				</td>
	 				<td valign="top"><span class="lable" style="margin-left: 20px;">菜单描述:</span></td>
	 				<td valign="top"><input name="des" class="input noinput" title="菜单描述" style="width: 300px;"></td>
	 			</tr>
	 		</table>
	 		<table style="margin-left: 10px;margin-top: 10px;">
	 			<tr>
	 				<td><span class="lable">是否启用:</span></td>
	 				<td>
		 				<select name="isused" class="select" title="是否启用">
		 					<option value="1">启用</option>
		 					<option value="0">禁用</option>
		 				</select>
	 				</td>
	 				<td><span class="lable" style="margin-left: 10px;">URL:</span></td>
	 				<td><input name="url" class="input noinput" style="width: 615px;" title="URL"></td>
	 			</tr>
	 		</table>
	 	</div>
	 </div>
	
  </body>
  <script type="text/html">
        <div class="radiobox">
            <input type="radio" name="1" />
            <input type="radio" name="1" checked="checked" />
        </div>
	</script>
</html>
