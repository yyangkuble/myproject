<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Spring MVC Plus角色管理</title>
    
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
		var mygrid;
		$(function(){
			var tabWidth=[200,300,150];
			var tabTitle={"name":"角色名称","des":"角色描述","btn":"操作"};
			mygrid=new MyGrid(tabWidth,tabTitle,"mygrid");
			search();
		});
		//修改sql
		function updatesql(element){
			if($(element).text()=="修改"){
				mygrid.editGrid($(element).parent(),["name","des"]);
				$(element).text("保存");
			}else{
				var roleObj = mygrid.getEditGrid($(element).parent());
				roleObj["id"]=$(element).attr("data");
				if(confirm("您确定修改吗")){
					roleObj["log"]="编辑角色";
					post("update/Role",roleObj,function(data){
						if(data==1){
							search();
							alert("修改成功");
						}
					},"json");
				}
			}
			
		}
		function editrole_menu(roleid){
			var thedata=new Object();
			thedata["id"]=roleid;
			openurl("plus/role_menu",thedata);
		}
		//添加角色
		function addSQLDisplay(){
			var thedata=new Object();
			var biaoshi=Math.uuid();
			thedata["name"]="";
			thedata["des"]="";
			thedata["btn"]='<span id="'+biaoshi+'" onclick="add(this)" class="gridlike">确定添加</span>';
			mygrid.addrow(thedata);
			mygrid.editGrid($("#"+biaoshi).parent(),["name","des"]);
		}
		//确定添加角色
		function add(element){
			var roleObj = mygrid.getEditGrid($(element).parent());
			roleObj["log"]="添加角色";
			post("insert/Role",roleObj,function(data){
				alert("添加成功");
				search();
			},"json");
		}
		//删除sql
		function deletesql(roleid){
			if(confirm("确定要删除这个角色吗?")){
				$post("delete/Role",{id:roleid,log:"删除角色"},function(data){
					if(data==1){
						alert("删除成功");
						search();
					}
				},"json");
			}
		}
		//查询
		function search(){
			$post("select/role_all/all",{},function(list){
				mygrid.clear();
				for(var i=0;i<list.length;i++){
					var thedata=list[i];
					thedata["btn"]='<span data="'+thedata["id"]+'" onclick="updatesql(this)" class="gridlike">修改</span><span onclick="deletesql(\''+thedata["id"]+'\')" class="gridlike">删除</span><span onclick="editrole_menu(\''+thedata["id"]+'\')" class="gridlike">权限</span>';
					mygrid.addrow(thedata);
				}
			},"json");
		}
		//刷新页面
		function reload(){
			openurl("plus/roleManager");
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
	    	</div>
	    	<div class="title" onclick="addSQLDisplay()" style="width: 80px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="新增角色">新增角色</div>
	    	<div class="title" onclick="reload()" style="width: 60px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="重载页面"><i class="fa fa-rotate-left"></i></div>
	    </div>
	    <div id="mygrid" class="ub-f1 scrollbar" style="overflow: auto;border-bottom: #EDEDED 1px solid;padding-left: 5px;">
    		
	    </div>
	 </div>
  </body>
  
</html>
