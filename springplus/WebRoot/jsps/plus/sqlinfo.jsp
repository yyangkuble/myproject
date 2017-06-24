<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Spring MVC PlusSQL详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@include file="js_css.jsp" %>
	<script type="text/javascript">
		var requestid ="<%=request.getAttribute("requestid") %>";
		$(function(){
			//初始化grid
			var tabWidth=[500,150,150,150,150,150];//每个列的宽度px
			//{"绑定json对象的key":"grid显示的标题名称"}
			var tabTitle={"sql_txt":"sql语句","starttime":"请求时间","endtime":"完成时间","usetime":"使用时间","isasyn":"是否多线程","isredis":"是否取缓存"};
			//初始化grid,mygrid:div的id,将grid初始化到那个容器里
			mygrid=new MyGrid(tabWidth,tabTitle,"mygrid");
			search();
		});
		function search(){
			post("select/select_sql_by_requestid/all",{requestid:requestid},function(list){
				mygrid.clear();
				for(var i=0;i<list.length;i++){
					var thedata=list[i];
					mygrid.addrow(thedata);
				}
			},"json");
		}
	</script>
  </head>
  
  <body>
      <div id="mygrid" class="ub-f1 scrollbar" style="overflow: auto;border-bottom: #EDEDED 1px solid;padding-left: 5px;">
    		
	    </div>
  </body>
</html>
