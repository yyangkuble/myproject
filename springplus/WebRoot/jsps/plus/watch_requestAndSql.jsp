<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Spring MVC Plus性能监控</title>
    
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
			var tabWidth=[350,150,150,150,150,150,150];//每个列的宽度px
			//{"绑定json对象的key":"grid显示的标题名称"}
			var tabTitle={"url":"url","starttime":"请求时间","usetime":"使用时间","sqlallusetime":"sql使用时间","sqlcount":"sql总数","javausetime":"程序使用时间","btn":"操作"};
			//初始化grid,mygrid:div的id,将grid初始化到那个容器里
			mygrid=new MyGrid(tabWidth,tabTitle,"mygrid");
			search(1);
		});
		//查询
		function search(page){
			$post("select/watch_request/"+page,{maxrow:mygrid.maxrow},function(data){
				mygrid.clear();
				var list=data["list"];
				for(var i=0;i<list.length;i++){
					var thedata=list[i];
					thedata["btn"]='<span data="'+thedata["requestid"]+'" onclick="sqlinfo(this)" class="gridlike">查看SQL</span>';
					mygrid.addrow(thedata);
				}
				mygrid.initpage(data["page"],data["allpage"],data["max"]);
			},"json");
		}
		function sqlinfo(element){
			window.open("plus/sqlinfo?requestid="+$(element).attr("data"), "_blank");
		}
		//刷新页面
		function reload(){
			openurl("plus/requestAndSqlWatch");
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
	    	<div class="title" onclick="downloadexcel()" style="width: 80px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="确定">导出Excel</div>
	    	<div class="title" onclick="add()" style="width: 80px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="新增用户">新增用户</div>
	    	<div class="title" onclick="reload()" style="width: 60px;margin-right: 10px;text-align: center;line-height: 40px;font-size: 15px;cursor: pointer;" title="重载页面"><i class="fa fa-rotate-left"></i></div>
	    </div>
	    <div style="padding: 10px;height: 50px;background-color: RGBA(244,244,244,0.5);border-bottom: #EDEDED 1px solid;">
	    	<table>
	    		<tr>
	    			<td><span class="lable">关键词:</span></td><td><input id="guanjianci" class="input"></td><td><span class="btn" style="margin-left: 10px;" onclick="search(1)">查询</span></td>
	    			<td>
	    				<span class="file" style="margin-left: 20px;">选择文件<input onchange="filechange(this)" id="testfile" name="testfile" type="file"></span>
	    			</td>
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
