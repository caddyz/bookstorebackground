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
<script type="text/javascript" src="<%=path%>/js/log/logAction.js"></script>
<script type="text/javascript" src="<%=path%>/js/datagridTools.js"></script>

<title>日志管理</title>

</head>
<body>

<div id="time"></div>
	<table id="dg" title="后台管理日志" class="easyui-datagrid" onClickRow="ClickRow" toolbar="#toolBar" > </table>
	<div id="toolBar">
	<input id="operate" class="easyui-searchbox" style="width: 100px" data-options="searcher:doSearch,prompt:'请输入关键字'"></input>

	&emsp;
	部门:
	<input id="dept" class="easyui-combobox" style="width: 100px" />
				IP:<input id="requestIp" style="width: 100px" />
				操作者:<input id="adminId" style="width: 100px" />
	&emsp;
	日志类型:
	<select id="operateType" class="easyui-combobox" style="width: 80px;">
			<option >所有类型</option>
			<option>错误</option>
			<option >未知</option>
			<option >登陆与注销</option>
			<option >数据库操作</option>
			<option >日志操作</option>
   </select> 
			&emsp;
		
			&emsp;
			 自<input id="startTime" type="text"
			class="easyui-datebox" style="width: 100px" /> 
			&ensp;
			至
			&ensp;<input
			id="endTime" type="text" class="easyui-datebox" style="width: 100px" />
		<br> 
			<a  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="openDelDg()">清除日志	</a> 
			<input type="checkbox"  onclick="showDetail(this,'#dg','detail')">显示细节
	</div>



	<div id="delLog" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true"  buttons="#dlg-buttons"  modal="true">
	
			<div class="ftitle">自定义时间</div>
			<input name="choose" type="radio" value="custom" >自定义
			<div class="fitem">
				<label>自</label> <input id="customStart" type="text" style="width: 100px;" class="easyui-datebox" >
			</div>
				<div class="fitem">
				<label>至</label> <input id="customEnd" type="text" style="width: 100px;" class="easyui-datebox" >
			</div>
			<div class="ftitle">预设时间</div>
			<input name="choose" type="radio" value="aWeekAgo" >两周之内
			<input name="choose" type="radio" value="aMonthAgo" >一个月之内
			<input name="choose" type="radio" value="threeMonthAgo" >三月个以内
		
	</div>
	
		<div id="dlg-buttons">
	
			<button onclick='confirm()' >提交</button>
	 	</div>
	
	
	
    
        

	
	
	
	
	
</body>

	
</html>