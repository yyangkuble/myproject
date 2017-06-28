<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<base href="<%=basePath%>">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>查询商品列表</title>
		</head>
	<body> 
		<form action="item/queryitem.action" method="post">
			查询条件：
			<table width="100%" border=1>
				<tr>
				<td>
					商品名称: <input name="items.name" type="text"/>
					商品价格: <input name="items.price" type="text"/>
					<input type="submit" value="查询"/>
				</td>
				</tr>
			</table>
		</form>
		<form action="item/updateitems.action" method="post">
			商品列表：<input type="submit" value="批量修改"/>
			<table width="100%" border=1>
				<tr>
					<td>商品名称</td>
					<td>商品价格</td>
					<td>生产日期</td>
					<td>商品描述</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${itemList }" var="item" varStatus="status">
					<tr>
						<td>
						<input type="text" value="${item.id }" name="itemsList[${status.index}].id" style="display: none;">
						<input type="text" value="${item.name }" name="itemsList[${status.index}].name"> </td>
						<td><input type="text" value="${item.price }" name="itemsList[${status.index}].price"></td>
						<td><input type="text" value="<fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="itemsList[${status.index}].createtime"></td>
						<td><input type="text" value="${item.detail }" name="itemsList[${status.index}].detail"></td>
						<td><a href="item/toItemEdit.action?id=${item.id}">修改</a></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</body>
</html>