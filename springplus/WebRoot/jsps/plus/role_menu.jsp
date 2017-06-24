<%@page import="www.springmvcplus.com.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String roleid=(String)request.getAttribute("id");
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
		var roleid="<%=roleid%>";
		var mygrids=new Object();
		var menus;
		$(function(){
			post("select/menuid_by_roleid/all",{roleid:roleid},function(data){
				menus=data;
			},"json");
			
			post("select/menu_by_parentid/all",{parentid:"0"},function(parents){
				for(var i=0;i<parents.length;i++){
					var thedata=parents[i];
					var tabWidth=[50,150];
					var tabTitle={"check":"选择","name":thedata["name"]};
					$("#rolegrids").append('<div id="'+thedata["id"]+'" style="width: 200px"></div>');
					var mygrid = mygrids[thedata["id"]]=new MyGrid(tabWidth,tabTitle,thedata["id"]);
					post("select/menu_by_parentid/all",{parentid:thedata["id"]},function(zimenus){
						for(var j=0;j<zimenus.length;j++){
							var zimenu=zimenus[j];
							zimenu["check"]='<div class="checkbox"><input id="'+zimenu["id"]+'"'+(check_have_menu(zimenu["id"])?'checked="checked"':"")+' type="checkbox"></div>';
							mygrid.addrow(zimenu);
						}
					},"json");
				}
			},"json");
		});
		//多条操作语句在一个事物里进行
		function editmenus(){
			var sqls=new Sqls();
			//删除权限
			sqls.add("exc/delete_sys_role_menu",{roleid:roleid});
			//获取勾选的权限  循环添加权限
			$("input:checked").each(function(){
				sqls.add("insert/Role_menu",{roleid:roleid,menuid:$(this).attr("id")});
			});
			var sqlsdata=sqls.get();
			sqlsdata["log"]="编辑角色对应的菜单";
			post("excs",sqlsdata,function(data){
				alert("修改完毕");
			},"json");
		}
		function check_have_menu(menuid){
			var is_have_menu=false;
			for(var i =0;i< menus.length;i++){
				var menu=menus[i];
				if(menu["menuid"]==menuid){
					is_have_menu=true;
					break;
				}
			}
			return is_have_menu;
		}
		
	</script>
  </head>
  
  <body>
	<div class="ub ub-ver" style="height: 100%;">
	    <div class="ub" style="height: 40px;background-image: url('resource/imgs/head-ico1.png');background-size:40px 40px;">
	    	<div class="ub ub-f1" style="height: 100%;line-height: 40px;padding-left: 10px;font-size: 13px;">
	    		<div id="menu1Name"><%= request.getAttribute("menu1Name") %></div>
	    		<div style="padding-left:17px;height: 100%;background-image: url(resource/imgs/head-ico.png);margin-left: 10px;margin-right: 15px;"></div>
	    		<div id="menu2Name"><%= request.getAttribute("menu2Name") %></div>
	    		<div style="padding-left:17px;height: 100%;background-image: url(resource/imgs/head-ico.png);margin-left: 10px;margin-right: 15px;"></div>
	    		<div>权限管理</div>
	    		<div style="padding-left:17px;height: 100%;background-image: url(resource/imgs/head-ico.png);margin-left: 10px;margin-right: 15px;"></div>
	    	</div>
	    	<div class="title" onclick="editmenus()" style="width: 80px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="确定修改">确定修改</div>
	    </div>
	    <div id="rolegrids" class="ub-f1 scrollbar ub" style="overflow: auto;border-bottom: #EDEDED 1px solid;padding-left: 5px;">
    		
	    </div>
	 </div>
  </body>
  
</html>
