
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.app.project.mode.Menu"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<Menu> menusparent=(List<Menu>)request.getAttribute("menusparent");
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Spring MVC PlusSpring MVC Plus</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@include file="js_css.jsp" %>
	<script type="text/javascript" src="resource/js/template.js"></script>
	<style type="text/css">
		html,body{
			margin: 0;padding: 0;height: 100%;width: 100%;overflow: hidden;
		}
		div#layoutdiv {
			position: relative;
			width: 100%;height: calc(100% - 100px);
		}
		div.menu > div{
			border-radius:12px;cursor: pointer;height: 24px;line-height: 24px;margin-top:4px;font-size: 15px;font-weight: 800;padding-left: 15px;padding-right: 15px;
		}
		.menunoactive{
			color: #00CC99;
		}
		.menuactive{
			color: #00CC99;background-color:#EBEBEB;
		}
		.menunoactive:hover{
			color: #FFFF66;
		}
		.menu2active{
			height: 40px;border-radius:3px;font-size: 14px;cursor: pointer;border-bottom: 2px solid #02689A;background-color: #7FBFC8;
		}
		.menu2noactive{
			height: 40px;background-color: #F3F3F3;border-radius:3px;font-size: 14px;cursor: pointer;border-bottom: 2px solid RGBA(223,223,223,0.5);
		}
		.menu2noactive:hover{
			height: 40px;border-radius:3px;font-size: 14px;cursor: pointer;border-bottom: 2px solid RGBA(223,223,223,0.5);background-color: #7FBFC8;
		}
	</style>
	<script type="text/javascript">
		var myLayout;
		var myAcc;
		$(function(){
			//setCookie("username","testusername");
			myLayout = new dhtmlXLayoutObject({
				parent:"layoutdiv",
				pattern:"2U",
				offsets: {
			        top: 0,
			        right: 0,
			        bottom: 0,
			        left: 0
			    }
			});
			myLayout.cells("b").hideHeader();
			myLayout.cells("a").setWidth(300);
			myLayout.cells("a").setCollapsedText("点击展开列表");
			//myLayout.cells("a").fixSize(false, true);//是否允许改变大小
			init_layout_juli();
			myLayout.attachEvent("onCollapse", function(name){//折
				init_layout_juli();
			});
			myLayout.attachEvent("onExpand", function(name){//叠
				init_layout_juli();
			});
			myLayout.attachEvent("onPanelResizeFinish", function(name){//拖拽layout 改变大小
				init_layout_juli();
			});
			$(window).resize(function(){//重置窗口大小
				myLayout.setSizes();
				init_layout_juli();
			});
			//初始化子菜单
			var firstParent=JSON.parse('<%= JSON.toJSONString(menusparent.get(0)) %>');
			menu1click(firstParent["id"],firstParent["name"]);
			//initwebsocket();
		});
		function openframe(theurl,param){
			myLayout.cells("b").attachURL(theurl,null,param);
		}
		//后台推送消息入口,不需要手动调用，使用前先调用initwebsocket();
		function onNewMessage(message){
			alert("有新消息:"+message);
		}
		//初始化子菜单
		function menu1click(parentId,parentName){
			myLayout.cells("a").setText("<span style='color:white;font-weight:800;font-size:15px;margin-left:100px;'>"+parentName+"</span>");
			post("select/menu_by_parentid_and_userid/all",{parentid:parentId,userid:user["id"]},function(data){
				if((data!="")){
					var menu_html=template("temelate_menu2", {data:data,parentname:parentName});
					myLayout.cells("a").attachHTMLString(menu_html);
					myLayout.cells("b").attachURL(data[0]["url"],null,{menu1Name:parentName,menu2Name:data[0]["name"]});
				}else{
					myLayout.cells("a").attachHTMLString("");
					myLayout.cells("b").attachHTMLString("");
				}
				//改变主菜单样式
				$(".menu > .menuactive").addClass("menunoactive");
				$(".menu > .menuactive").removeClass("menuactive");
				$("#"+parentId).addClass("menuactive");
				$("#"+parentId).removeClass("menunoactive");
			},"json");
		}
		function menu2click(element,url,parentname,name){
			//改变主菜单样式
			$(".menu2 > .menu2active").addClass("menu2noactive");
			$(".menu2 > .menu2active").removeClass("menu2active");
			$(element).addClass("menu2active");
			$(element).removeClass("menu2noactive");
			openframe(url,{menu1Name:parentname,menu2Name:name});
		}
		function init_layout_juli(){
			var layoutjuli=8;
			var dhxlayout_cont = $(".dhxlayout_cont > div");
			var allwidth=parseInt($(".dhxlayout_cont").css("width").replace("px",""));
			var leftwidth=parseInt(dhxlayout_cont.eq(0).css("width").replace("px",""));
			dhxlayout_cont.eq(2).css("width",layoutjuli);
			dhxlayout_cont.eq(1).css({"left":(leftwidth+layoutjuli),"width":(allwidth-layoutjuli-leftwidth)});
			dhxlayout_cont.eq(1).children(".dhx_cell_cont_layout").css("width",allwidth-layoutjuli-leftwidth-2);
		}
		function exit(){//退出
			window.location.href="login";
		}
    </script>  
  </head>
  
  <body>
    		<div style="background-image: url('resource/imgs/beijing_top.jpg');background-size: cover;height: 100px;">
    			<div style="height: 66px;padding-left: 50px;" class="ymiddle"><span style="font-size: 40px;">BLUEX</span><span style="font-size: 30px;margin-left: 10px;">通用框架</span></div>
    			<div style="height: 1px;background-color: #7DC0C9;"></div>
    			<div class="ub" style="height: 32px;background-color:#006699;box-shadow: 0px 5px 20px #6BB7C6 inset">
    				<div style="height: 100%;width:300px;font-size: 13px;line-height: 32px;"><span style="margin-left: 16px;">欢迎宋荣洋进入=》</span><a style="color: #660000;cursor: pointer;" onclick="exit()">退出</a></div>
    				<div class="ub-f1 ub menu" style="height: 100%;">
    				<% for(int i=0;i<menusparent.size();i++){ 
    					Menu menu=menusparent.get(i);
    				%>
    					<div id="<%= menu.getId() %>" onclick="menu1click('<%= menu.getId() %>','<%= menu.getName() %>')" class="<%=i==0?"menuactive":"menunoactive" %> noselect"><i style="" class="fa fa-dot-circle-o"></i><a style="margin-left: 5px;"><%= menu.getName() %></a></div>
    				<%} %>
    				</div>
    			</div>
    			<div style="height: 1px;background-color: #003366;"></div>
    		</div>
    		<div id="layoutdiv"></div>
			
			
			


<script id="temelate_menu2" type="text/html">
	<div id="menu2" class="scrollbar menu2" style="height:100%;overflow-Y: auto;background-color: RGBA(51,153,204,0.2);">
		{{each data}}
		<div id="menu2{{$value.id}}" class="ub {{if $index ==0}}menu2active{{else}}menu2noactive{{/if}}"  onclick="menu2click(this,'{{$value.url}}','{{parentname}}','{{$value.name}}')">
					<div style="height: 100%;width: 28px;line-height: 40px;text-align:right;padding-right:10px;"><i class="fa fa-caret-square-o-right"></i></div>
					<div class="ub-f1 noselect" style="line-height: 40px;">{{$value.name}}</div>
					<div style="height: 20px;width: 30px;background-color: #428BCA;border-radius:15px;color: white;text-align: center;line-height: 22px;margin-top: 10px;font-size: 13px;margin-right: 5px;">99</div>
		</div>
		{{/each}}
	</div>
</script>
  </body>
</html>
