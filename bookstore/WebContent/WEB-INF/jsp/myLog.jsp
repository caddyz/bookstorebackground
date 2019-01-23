<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.print("basePath : " + basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/jquery-easyui-1.4.5/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/jquery-easyui-1.4.5/demo/demo.css">
	<link rel="stylesheet" href="<%=path%>/css/wsk.css">
<script type="text/javascript" src="<%=path%>/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=path%>/js/log/myLog.js"></script>
<script type="text/javascript" src="<%=path%>/js/datagridTools.js"></script>

<title>操作历史</title>

</head>
<body>

<div id="time"></div>
	<table id="dg" title="" class="easyui-datagrid" onClickRow="ClickRow" toolbar="#toolBar" > </table>
	<div id="toolBar">
	<input id="operate" class="easyui-searchbox" style="width: 100px" data-options="searcher:doSearch,prompt:'请输入关键字'"></input>

	&emsp;
				IP:<input id="requestIp" style="width: 100px" />
	&emsp;
	日志类型:
	<select id="operateType" class="easyui-combobox" style="width: 100px;">
			<option >所有类型</option>
			<option>错误</option>
			<option >未知</option>
			<option >登陆与注销</option>
			<option >数据库操作</option>
			<option >日志操作</option>
   </select> 
			&emsp;
		日志时间:
			&emsp;
			 自&ensp;
			 <input id="startTime" type="text"
			class="easyui-datebox" style="width: 100px" /> 
			&ensp;
			至
			&ensp;<input
			id="endTime" type="text" class="easyui-datebox" style="width: 100px" />
		<br> 
			<input type="checkbox"  onclick="showDetail(this,'#dg','detail')">显示细节
			
	</div>
	
</body>

	
</html>