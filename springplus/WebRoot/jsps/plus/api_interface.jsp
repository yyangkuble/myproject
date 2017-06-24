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
			var tabWidth=[200,600,150];//每个列的宽度px
			//{"绑定json对象的key":"grid显示的标题名称"}
			var tabTitle={"modename":"分类","describle":"描述","btn":"测试"};
			//初始化grid,mygrid:div的id,将grid初始化到那个容器里
			mygrid=new MyGrid(tabWidth,tabTitle,"mygrid");
			initModeName();
			search();
		});
		function initModeName(){
			post("select/findApiModeName/all",{},function(data){
				for(var i=0;i<data.length;i++){
					$("#modenameselect").append("<option value='"+data[i]["modename"]+"'>"+data[i]["modename"]+"</option>");
				}
			},"json");
		}
		//查询
		function search(){
			var modenameOption =$("#modenameselect").find("option:selected");
			var modename = modenameOption.val();
			mygrid.clear();
			post("select/findApiByModename/all",{modename:modename},function(data){
				for(var i=0;i<data.length;i++){
					var thedata=data[i];
					thedata["btn"]='<span data="'+thedata["id"]+'" onclick="test(this)" class="gridlike">测试</span>';
					mygrid.addrow(thedata);
				}
			},"json");
			
		}
		function test(element){
			var isZhijie='request.getAttribute("menu1Name")==null';
			var apiId = $(element).attr("data");
			var theobj=new Object();
			theobj["id"]=apiId;
			if(isZhijie){
				open("plus/api_interface_test?id="+apiId,"_blank");
			}else{
				openurl("plus/api_interface_test",theobj);
			}
			
		}
	</script>
  </head>
  
  <body>
	  <div class="ub ub-ver" style="height: 100%;">
	    <div class="ub" style="height: 40px;background-image: url('resource/imgs/head-ico1.png');background-size:40px 40px;">
	    	<div class="ub ub-f1" style="height: 100%;line-height: 40px;padding-left: 10px;font-size: 13px;">
	    		<div id="menu1Name"><%= request.getAttribute("menu1Name")==null?"接口":request.getAttribute("menu1Name") %></div>
	    		<div style="padding-left:17px;height: 100%;background-image: url(resource/imgs/head-ico.png);margin-left: 10px;margin-right: 15px;"></div>
	    		<div id="menu2Name"><%= request.getAttribute("menu2Name")==null?"接口":request.getAttribute("menu2Name") %></div>
	    		<div style="padding-left:17px;height: 100%;background-image: url(resource/imgs/head-ico.png);margin-left: 10px;margin-right: 15px;"></div>
	    	</div>
	    </div>
	    <div style="padding: 10px;height: 50px;background-color: RGBA(244,244,244,0.5);border-bottom: #EDEDED 1px solid;">
	    	<table>
	    		<tr>
	    			<td><span class="lable">分类:</span></td><td><select id="modenameselect" onchange="search(1)" class="select" style="margin-right: 10px;"></select></td>
	    		</tr>
	    	</table>
	    </div>
	    <div id="mygrid" class="ub-f1 scrollbar" style="overflow: auto;border-bottom: #EDEDED 1px solid;padding-left: 5px;">
    		
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
