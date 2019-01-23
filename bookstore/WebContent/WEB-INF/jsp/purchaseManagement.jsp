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
<script type="text/javascript" src="<%=path%>/js/purchase/purchaseAction.js"></script>
<script type="text/javascript" src="<%=path%>/js/purchase/purchaseItemAction.js"></script>
<script type="text/javascript" src="<%=path%>/js/purchase/purchaseValidate.js"></script>
<script type="text/javascript" src="<%=path%>/js/datagridTools.js"></script>

<title>采购管理</title>

</head>
<body>

<div>

</div>
	<table id="dg" title="采购信息" class="easyui-datagrid" onClickRow="ClickRow" toolbar="#toolBar" >
		
	</table>
	<div>

<span id="tips" style="left:310px;"> </span>

</div>


	<div id="toolBar">
	<span>&emsp;查询条件：</span>
	&emsp;
	采购批次
	<input id="searchBatchC" class="easyui-combobox" style="width: 100px" />
	&emsp;
	采购状态
	<select id="searchStatus" class="easyui-combobox" style="width: 100px;">
			<option value=''>全部状态</option>
			<option value='1'>计划中</option>
			<option value='2'>进行中</option>
			<option value='3'>待入库</option>
			<option value='4'>已完成</option>
			<option value='0'>已取消</option>
   </select> 
			&emsp;
		
			&emsp;
			 自<input id="startDate" type="text"
			class="easyui-datebox" style="width: 100px" /> 
			&ensp;
			至
			&ensp;<input id="endDate" type="text" class="easyui-datebox" style="width: 100px" />
			
			&emsp; <span id="tips"></span>
		<br> 
	
		&emsp;&emsp;	<button  id="searchButton" onclick="doSearch()"  style="width: 100px" >开始查询</button>
			&emsp;&emsp;&emsp;<a	 class="easyui-linkbutton" iconCls="icon-add" plain="true"	onclick="postPurchase()">添加采购记录</a> 
			 <a  class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="putPurchase()">修改记录</a>
			<a  class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delPurchase()">删除采购记录</a> 
			<br> 		
	</div>
	
	

	<div id="PurchaseInfo" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons"  modal="true">
		<div class="ftitle">采购信息</div>
		<form id="fm" method="post" >
			<div class="fitem">
			 <input name="purchaseId" type="hidden">
			</div>
			<div class="fitem">
				<label>采购批次:</label> <input id="postBatch" name="purchaseBatch" class="easyui-textbox"  style="width: 120px">
			</div>
			<div class="fitem" id="purchaseMan">
				<label>采购人:</label> 
				<input id="purchaseEmployee" name="employee" class="easyui-textbox"  readonly="true" >
			</div>
			<div class="fitem">
				<label>供应商:</label> 	<input id="postSupplier" name="supplier" class="easyui-combobox" style="width: 120px" />
			</div>
			<div class="fitem" id="purchaseStatusFm">
				<label>采购状态:</label>
	<!-- 	<select id="searchStatus" class="easyui-combobox" name="purchaseStatus" style="width: 120px;">
			<option class="status1" value='1'>计划中</option>
			<option class="status2" value='2'>进行中</option>
			<option class="status2" value='3'>待入库</option>
			<option class="status3" value='4'>已完成</option>
			<option class="status1" value='0'>已取消</option>
			</select>  -->
			<span class="status1"><input  type="radio" name="purchaseStatus" value="计划中">计划中</span>
			<span class="status2"><input  type="radio" name="purchaseStatus" value="进行中">进行中</span>
			<span class="status2"><input  type="radio" name="purchaseStatus" value="待入库">待入库</span>
			<span class="status3"><input  type="radio" name="purchaseStatus" value="已完成">已完成</span>
			<span class="status0"><input  type="radio" name="purchaseStatus" value="已取消">已取消</span> 
			</div>
			<div class="fitem">
				<label>采购日期:</label> <input name="purchaseDate" type="text" style="width: 120px;"
					class="easyui-datebox" >
			</div>
			<div class="fitem">
				<label>采购说明:</label>
				<textarea name="purchaseLog"  style="width: 110px"> </textarea>
			</div>
			
			
			
		</form>
	
	</div>
	
		<div id="dlg-buttons">
		
	
	 	</div>
	
	<div id="PIInfo" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true">
		<div class="ftitle">条目 <span id="tipsPII"> </span>	</div>
		<div id="buttonPII">
		<button onclick="appendPII()" class="easyui-linkbutton"  iconCls="icon-add">添加</button>
		<button onclick="removePII()" class="easyui-linkbutton"  iconCls="icon-remove">删除</button>
		&emsp;&emsp;&emsp;
		<button onclick="postPIIbatch()" class="easyui-linkbutton"  iconCls="icon-ok" >应用</button>
		</div>
		
		
			<div class="fitem">
				<label>商品ID</label>
				<input id="bookPII" class="easyui-textbox">
			</div>
			<div class="fitem">
				<label>数量</label>
				 <input id="numberPII" class="easyui-textbox"  data-options="required:true,validType:'money'"  >
			</div>
			<div class="fitem">
				<label>价格</label> 
				<input id="pricePII" class="easyui-textbox"   data-options="required:true,validType:'number'" >
			
			</div>
		
			<table id="dataPII"></table>  
	
	</div>
	
	
	 <div id="purchasePro" class="easyui-dialog" style=" padding: 10px 20px; background:rgba(128,128,128,0.3)" maximizable="true" closed="true" modal="true">	
     <h2 style="color:rgba(255,0,0,0.8);text-align:center" id='titlePro'></h2>
     <div id="profile">
     <ul id="top1">
   <li> 批次：<span id='batchPro'></span></li>
  	<li> 日期：<span id='datePro'></span></li>
    <li>员工：<span id='empPro'></span></li>
    </ul>
    <ul id="top2">
    <li>供应商：<span id='suppliePro'></span></li>
    <li>采购状态：<span id='statusPro'></span></li>
    <li>采购总额：<span id="totalPay"></span></li>
     </ul>
     </div>
    <div id="bottom1">	
    <table id="dgPro" title="采购清单" class="easyui-datagrid" >
    </table>
    </div>  
	<div id="toolBarPro">
			<a	href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"	onclick="openPII()">添加</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delPI()">删除</a> 
			 <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearSelections('#dgPro')">取消选择</a>

	</div>


    </div>
    

	
	
	
	
	
</body>

	
</html>