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
	</style>
	<script type="text/javascript">
		var mygrid;
		$(function(){
			//初始化grid
			var tabWidth=[150,150,150,150,150,150,250];//每个列的宽度px
			//{"绑定json对象的key":"grid显示的标题名称"}
			var tabTitle={"username":"用户名","name":"姓名","tel":"电话","addtime":"注册时间","islock":"是否锁定","approle":"app角色","btn":"操作"};
			//初始化grid,mygrid:div的id,将grid初始化到那个容器里
			mygrid=new MyGrid(tabWidth,tabTitle,"mygrid");
			mygrid.maxrow=20;//默认20条数据
			search(1);
		});
		//修改
		function update(element){
			openurl("plus/user_add_update",{"type":"update","id":$(element).attr("data")});
		}
		//添加
		function add(){
			openurl("plus/user_add_update");
		}
		//删除
		function del(id){
			if(confirm("确定要删除这个用户吗?")){
				$post("delete/User",{id:id,log:"删除用户"},function(data){
					if(data==1){
						alert("删除成功");
						openurl("plus/userManager");
					}
				},"json");
			}
		}
		//编辑用户角色
		function edituserrole(id){
			var user_roles="";
			//加载用户权限
			post("select/role_slect_by_userid/all",{userid:id},function(data){
				for(var i=0;i<data.length;i++){
					user_roles+=data[i]["roleid"]+",";//创造自动填充form表单的数据  checkbox的值用,号隔开
				}
			},"json");
			//初始化全部角色
			var tabWidth=[50,400];//每个列的宽度px
			//{"绑定json对象的key":"grid显示的标题名称"}
			var tabTitle={"checkbox":"选择","name":"角色"};
			//初始化grid,mygrid:div的id,将grid初始化到那个容器里
			var userRoleGrid=new MyGrid(tabWidth,tabTitle,"userrolegrid");
			post("select/role_all/all",{},function(data){
				for(var i=0;i<data.length;i++){
					var therole=data[i];
					therole["checkbox"]='<div class="checkbox"><input name="roles" value="'+therole["id"]+'" type="checkbox"></div>';
					userRoleGrid.addrow(therole);
				}
			},"json");
			fillForm("user_role_div",{roles:user_roles});
			$("#user_role_div").attr("data",id);
			$("#user_role_div").css("display","");
		}
		function queren_edituserrole(){
			var userid = $("#user_role_div").attr("data");
			var formdata=checkForm("user_role_div");
			var sqls=new Sqls();
			sqls.add("delete/User_role",{userid:userid});
			if(formdata["roles"] != undefined){
				var roles=formdata["roles"].split(',');
				for(var i=0;i<roles.length;i++){
					sqls.add("insert/User_role",{userid:userid,roleid:roles[i]});
				}
			}
			var sqlsdata=sqls.get();
			sqlsdata["log"]="编辑用户角色";
			post("excs",sqlsdata,function(data){
				alert("修改成功");
				$("#user_role_div").css("display","none");
			},"json");
		}
		function quxiao(){
			$("#user_role_div").css("display","none");
		}
		//解锁
		function unlock(id){
			post("update/User",{id:id,islock:0,log:"用户解锁"},function(data){
				if(data==1){
					alert("已解锁");
				}
			},"json");
		}
		//重置密码
		function resetpassword(id){
			post("update/User",{id:id,password:"password",log:"用户重置密码"},function(data){
				if(data==1){
					alert("已重置密码");
				}
			},"json");
		}
		//查询
		function search(page){
			var guanjianci=$.trim($("#guanjianci").val());
			var where="where ";
			if(guanjianci!=""){
				where += "username like '%"+guanjianci+"%' or tel like '%"+guanjianci+"%' or name like '%"+guanjianci+"%'";
			}
			if(where == "where "){
				where="";
			}
			$post("select/user_select_by_where/"+page,{where:where,maxrow:mygrid.maxrow},function(data){
				mygrid.clear();
				var list=data["list"];
				for(var i=0;i<list.length;i++){
					var thedata=list[i];
					if(thedata["islock"]==0){
						thedata["islock"]="正常";
					}else{
						thedata["islock"]="已锁定";
					}
					if(thedata["approle"]=="service"){
						thedata["approle"]="服务站";
					}else if(thedata["approle"]=="engineer"){
						thedata["approle"]="工程师";
					}else{
						thedata["approle"]="无app权限";
					}
					thedata["btn"]='<span data="'+thedata["id"]+'" onclick="update(this)" class="gridlike">修改</span>';
					thedata["btn"]=thedata["btn"]+'<span onclick="del(\''+thedata["id"]+'\')" class="gridlike">删除</span>';
					thedata["btn"]=thedata["btn"]+'<span onclick="edituserrole(\''+thedata["id"]+'\')" class="gridlike">角色</span>';
					thedata["btn"]=thedata["btn"]+'<span onclick="unlock(\''+thedata["id"]+'\')" class="gridlike">解锁</span>';
					thedata["btn"]=thedata["btn"]+'<span onclick="resetpassword(\''+thedata["id"]+'\')" class="gridlike">重置密码</span>';
					mygrid.addrow(thedata);
				}
				mygrid.initpage(data["page"],data["allpage"],data["max"]);
			},"json");
		}
		//刷新页面
		function reload(){
			openurl("plus/userManager");
		}
		//修改显示行数
		function updateMaxRow(maxRow){
			mygrid.maxrow=maxRow;
			updatePage(1);
		}
		function updatePage(page){//下一页，上一页，第一页，最后一页
			search(page);
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
	    	<div class="title" onclick="add()" style="width: 80px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="新增用户">新增用户</div>
	    	<div class="title" onclick="reload()" style="width: 60px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="重载页面"><i class="fa fa-rotate-left"></i></div>
	    </div>
	    <div style="padding: 10px;height: 50px;background-color: RGBA(244,244,244,0.5);border-bottom: #EDEDED 1px solid;">
	    	<table>
	    		<tr>
	    			<td><span class="lable">关键词:</span></td><td><input id="guanjianci" class="input"></td><td><span class="btn" style="margin-left: 10px;" onclick="search(1)">查询</span></td>
	    		</tr>
	    	</table>
	    </div>
	    <div id="mygrid" class="ub-f1 scrollbar" style="overflow: auto;border-bottom: #EDEDED 1px solid;padding-left: 5px;">
    		
	    </div>
	    <div id="pagediv" class="ub" style="height: 40px;background-image: url('resource/imgs/head-ico1.png');background-size:40px 40px;" class="ub">
	    		
	    </div>
	 </div>
	 <div id="user_role_div" class="ymiddle xcenter" style="height: 100%;position: absolute;width: 100%;background-color: RGBA(0,0,0,0.4);z-index: 10;left: 0;top: 0;display:none;">
	 	<div class="ub ub-ver" style="background-color: white;border-radius:5px;border: 5px solid RGBA(4,105,155,0.6);">
	 		<div id="userrolegrid" class="ub-f1" style="min-height: 400px;">
	 		</div>
	 		<div style="height: 50px;" class="xcenter ymiddle">
	 			<span class="btn" onclick="quxiao()">取消</span><span onclick="queren_edituserrole()" class="btn" style="margin-left: 50px;">确定</span>
	 		</div>
	 	</div>
	 </div>
  </body>
  
</html>
