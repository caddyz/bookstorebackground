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
<script type="text/javascript"
	src="<%=path%>/js/supplier/supplierAction.js"></script>
<title>供应商管理</title>
<style>
</style>
</head>
<body>

<div>

	<table id="dg" title="供应商列表" onClickRow="ClickRow"
		class="easyui-datagrid" 
		toolbar="#searchBar">

	</table>
</div>
<div>

<span id="tips"> </span>

</div>
	
	
	<div id="searchBar">
	<span>查询条件:</span>
		<input id="searchName" class="easyui-textbox" style="width: 150px"
			data-options="prompt:'请输入供应商关键字'" />
		合作状态 
		<select id="searchStatus" class="easyui-combobox" name="dept" style="width: 200px;">
			<option>全部状态</option>
			<option>未合作</option>
			<option>合作中</option>
			<option>合作过</option>
		</select> 
		日期：
		
		自<input id="startDate" type="text" class="easyui-datebox" style="width: 100px" /> 至<input id="endDate" type="text"class="easyui-datebox" style="width: 100px" /> 
		&emsp;
		&emsp;
	<input type="button" value="开始查询">		
		
		
		<br> 
		&emsp;
		<a	href="#dlg" class="easyui-linkbutton" iconCls="icon-add" plain="true"	onclick="postSupplier()">添加供应商</a> 
		&emsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delSupplier()">删除供应商</a> 
			
	</div>


 <div id="supplierPro" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true"  buttons="#dlg-buttons">
     <table id="pro"> 
     <caption id='titlePro'></caption>
    <tr><th>标题</th><th>内容</th></tr>
    <tr><td>ID</td><td id='idPro'></td></tr>
    <tr><td>座机</td><td id='phonePro'></td></tr>
    <tr><td>合作状态</td><td id='statusPro'></td></tr>
    <tr><td>合作时间</td><td id='datePro'></td></tr>
    <tr><td>联系人</td><td id='contactPro'></td></tr>
    <tr><td>联系电话</td><td id='cPhonePro' ></td></tr>
    
     </table>
        
</div>

 <div id="postSupplier" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
        buttons="#dlg-buttons"  modal="true">
        <div class="ftitle">供应商信息</div>
        <form id="fm" method="post">
    
                 <input  name="supplierId"  type="hidden">
         
        
            <div class="fitem">
                <label>供应商名称:</label> <input name="supplierName" class="easyui-textbox" required="required">
            </div>
            <div class="fitem">
                <label>固定电话:</label> <input name="supplierPhone" class="easyui-textbox" required="required">
            </div>
            <div class="fitem">
            <label>企业地址:</label> <input name="supplierAddress" class="easyui-textbox" required="required">
   	 </div>

    <div class="fitem">
        <label>合作日期:</label> <input name="cooperateDate" type="text" class="easyui-datebox" required="required">
    </div>
    <div class="fitem">
        <label>合作状态</label> 
        <input type="radio" name="cooperateStatus" value="0">未合作
        <input type="radio" name="cooperateStatus" value="1">合作中
        <input type="radio" name="cooperateStatus" value="2">合作过
    </div>
    <div class="fitem">
        <label>联系人:</label> <input name="supplierContact" class="easyui-textbox">
    </div>
    <div class="fitem">
        <label>联系电话:</label> <input name="supplierContactPhone" class="easyui-textbox">
    </div>


    </form>
    </div>
	<div id="dlg-buttons">
		
	</div>
</body>


	
</html>