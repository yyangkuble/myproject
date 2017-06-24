<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		.orderby{
			font-size: 18px;color: blue;cursor: pointer;margin-left: 20px;
		}
	</style>
	<script type="text/javascript">
		var mygrid;
		$(function(){
			$.ajaxSetup({async: false});//所有ajax全部同步操作
			var tabWidth=[200,200,100,100,70,100];
			var tabTitle={"name":"菜单名称","des":"菜单描述","orderbybtn":"排序","isused":"是否启用","url":"连接地址","btn":"操作"};
			mygrid=new MyGrid(tabWidth,tabTitle,"mygrid");
			search(1,'0');
			//填充菜单类别parentMenus
			$post("select/menu_by_parentid/all",{parentid:'0'},function(list){
				for(var i=0;i<list.length;i++){
					var thedata=list[i];
					var html='<option value="'+thedata["id"]+'">'+thedata["name"]+'</option>';
					$("#parentMenus").append(html);
				}
			},"json");
		});
		//修改sql
		function updatesql(element){
			var thedata=new Object();
			thedata["id"]=$(element).attr("data");
			thedata["type"]="update";
			openurl("plus/menu_add_update",thedata);
		}
		//新增菜单
		function addSQLDisplay(){
			openurl("plus/menu_add_update");
		}
		//删除sql
		function deletesql(menuid){
			if(confirm("确定要删除这个sql吗?")){
				$post("delete/Menu",{id:menuid,log:"删除菜单"},function(data){
					if(data==1){
						alert("删除成功");
						openurl("plus/meunManager");
					}
				},"json");
			}
		}
		//查询
		var pId;
		function search(page,parentid){
			pId=parentid;
			$post("select/menu_by_parentid/all",{parentid:parentid},function(list){
				mygrid.clear();
				var downMinNum;
				var downMaxNum;
				var upMinNum;
				var upMaxNum;
				for(var i=0;i<list.length;i++){
					var thedata=list[i];
					thedata["btn"]='<span data="'+thedata["id"]+'" onclick="updatesql(this)" class="gridlike">修改</span><span onclick="deletesql(\''+thedata["id"]+'\')" class="gridlike">删除</span>';
					if(thedata["isused"]==0){
						thedata["isused"]="禁用";
					}else{
						thedata["isused"]="启用";
					}
					if(list.length>=2){//大于1个才可以排序
						if(i==0){//如果是第一个不允许上移动
							downMinNum=list[i+1]["orderby"];
							if(i+2>list.length-1){//如果只有两个
								downMaxNum=downMinNum+20;
							}else{
								downMaxNum=list[i+2]["orderby"];
							}
							thedata["orderbybtn"]='<span class="orderby" style="margin-left:56px;" onclick="changeOrderBy(\''+parentid+'\',\''+thedata["id"]+'\','+downMinNum+','+downMaxNum+')" title="下移"><i class="fa fa-arrow-circle-o-down"></i></span>';
						}else if(i==list.length-1){//如果是最后一个 不允许向下移动
							if(i-2<0){//如果少于两个
								upMinNum=0;
							}else{
								upMinNum=list[i-2]["orderby"];
							}
							upMaxNum=list[i-1]["orderby"];
							thedata["orderbybtn"]='<span class="orderby" onclick="changeOrderBy(\''+parentid+'\',\''+thedata["id"]+'\','+upMinNum+','+upMaxNum+')" title="上移"><i class="fa fa-arrow-circle-o-up"></i></span>';
						}else{
							if(i-2<0){//如果少于两个
								upMinNum=0;
							}else{
								upMinNum=list[i-2]["orderby"];
							}
							upMaxNum=list[i-1]["orderby"];
							downMinNum=list[i+1]["orderby"];
							if(i+2>list.length-1){//如果只有两个
								downMaxNum=downMinNum+20;
							}else{
								downMaxNum=list[i+2]["orderby"];
							}
							thedata["orderbybtn"]='<span class="orderby" onclick="changeOrderBy(\''+parentid+'\',\''+thedata["id"]+'\','+upMinNum+','+upMaxNum+')" title="上移"><i class="fa fa-arrow-circle-o-up"></i></span><span class="orderby" onclick="changeOrderBy(\''+parentid+'\',\''+thedata["id"]+'\','+downMinNum+','+downMaxNum+')" title="下移"><i class="fa fa-arrow-circle-o-down"></i></span>';
						}
					}else{
						thedata["orderbybtn"]="";
					}
					
					mygrid.addrow(thedata);
				}
			},"json");
		}
		function changeOrderBy(parentid,menuId,minNum,maxNum){
			var orderbynum = getorderbynum(minNum,maxNum);
			$post("update/Menu",{id:menuId,orderby:orderbynum},function(data){
				search(0,parentid);
			},"json");
		}
		//刷新页面
		function reload(){
			openurl("plus/meunManager");
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
	    	<div class="title" onclick="addSQLDisplay()" style="width: 80px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="新增SQL">新增菜单</div>
	    	<div class="title" onclick="reload()" style="width: 60px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="重载页面"><i class="fa fa-rotate-left"></i></div>
	    </div>
	    <div style="padding: 10px;height: 50px;background-color: RGBA(244,244,244,0.5);border-bottom: #EDEDED 1px solid;">
	    	<table>
	    		<tr>
	    			<td><span class="lable">菜单类别:</span></td><td><select id="parentMenus" onchange="search(1,this.value)" class="select" style="width: 150px;">
	    				<option value="0">主菜单</option>
	    			</select></td>
	    		</tr>
	    	</table>
	    </div>
	    <div id="mygrid" class="ub-f1 scrollbar" style="overflow: auto;border-bottom: #EDEDED 1px solid;padding-left: 5px;">
    		
	    </div>
	    <div id="pagediv" class="ub" style="height: 40px;background-image: url('resource/imgs/head-ico1.png');background-size:40px 40px;" class="ub">
	    		
	    </div>
	 </div>
  </body>
  
</html>
