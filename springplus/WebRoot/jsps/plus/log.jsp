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
			var tabWidth=[150,150,150,400,150];//每个列的宽度px
			//{"绑定json对象的key":"grid显示的标题名称"}
			var tabTitle={"username":"用户名","name":"姓名","remoteaddress":"访问地址","des":"操作动作","updatetime":"操作时间"};
			//初始化grid,mygrid:div的id,将grid初始化到那个容器里
			mygrid=new MyGrid(tabWidth,tabTitle,"mygrid");
			mygrid.maxrow=20;//默认20条数据
			search(1);
		});
	
		//查询
		function search(page){
			var guanjianci=$.trim($("#guanjianci").val());
			var where="where ";
			if(guanjianci!=""){
				where += "username like '%"+guanjianci+"%' or name like '%"+guanjianci+"%' or des like '%"+guanjianci+"%'";
			}
			if(where == "where "){
				where="";
			}
			$post("select/log_select_by_where/"+page,{where:where,maxrow:mygrid.maxrow},function(data){
				mygrid.clear();
				var list=data["list"];
				for(var i=0;i<list.length;i++){
					var thedata=list[i];
					
					mygrid.addrow(thedata);
				}
				mygrid.initpage(data["page"],data["allpage"],data["max"]);
			},"json");
		}
		//刷新页面
		function reload(){
			openurl("plus/log");
		}
		//修改显示行数
		function updateMaxRow(maxRow){
			mygrid.maxrow=maxRow;
			search(1);
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
  </body>
  
</html>
