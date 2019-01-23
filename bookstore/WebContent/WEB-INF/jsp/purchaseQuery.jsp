<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
	System.out.print("basePath : " + basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<%=path%>/jquery-easyui-1.4.5/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/jquery-easyui-1.4.5/demo/demo.css">
<link rel="stylesheet" href="<%=path%>/css/wsk.css">
<script type="text/javascript" src="<%=path%>/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=path%>/js/purchase/purchaseItemAction.js"></script>
<script type="text/javascript" src="<%=path%>/js/purchase/purchaseValidate.js"></script>

<script type="text/javascript">
var url = "";// 定义一个全局变量传给id为fm的form提交事件


let $purchaseId  = '';
let $purchaseBatch  = '';
let $purchaseDate  = '';
let $purchaseStatus  = '';
let $purchaseLog  = '';
let $currentPage  = '';
let $pageSize  = '';
let $supplier  = '';
let $startDate  = '';
let $endDate  = '';
let $employee='';
let $admin='';
let $UniformResourceLocation='http://'+window.location.host+'/bookstore/';


	function doSearch(){ 
	
		$.ajax({			
			url:$UniformResourceLocation+'purchase/dynamicGet',
			dataType:'json',
			async:false,
			data:{
				
				purchaseId: $purchaseId,
				purchaseBatch		:$purchaseBatch,
				supplier		:$supplier,
				employee		:$employee,	
				startDate		:$startDate,
				endDate	:$endDate,
				purchaseStatus			:$purchaseStatus,
				currentPage			:1,
				pageSize			:10,
			},
			success:function(data){
				transferStatus(data.value.rows);
				transferEmp(data.value.rows);
				transferSupplier(data.value.rows) ;
				$("#dg").datagrid('loadData',data.value);				
				console.log(data);
			}
		});
		
   
	}
	
	$(function(){	
		$("#dg").datagrid({  
			   onDblClickRow: function (index, row){purchaseProFun(row);} ,
				onClickRow : function(index, row) {
					$("#tips").show();
					 $("#tips").html("双击查看查看采购详情");
					 $("#tips").css("color","rgba(255,0,0,0.9)");
					 $('#tips').delay(2000).hide(0);
				}
				
		            });
		
		$('#dg').datagrid({    
	 		 columns:[[    
	 		 {field:'purchaseId',hidden:true},    
	 		 {field:'purchaseBatch',title:'采购批次',width:100},    
	 		 {field:'purchaseDate',title:'采购日期',width:100,align:'center'}   ,
	 		 {field:'supplier',title:'供应商',width:100,align:'center'}    ,
	 		 {field:'employee',title:'员工',width:100,align:'center'}    ,
	 		 {field:'purchaseStatus',title:'采购状态',width:100,align:'center'}  ,
	 		 {field:'purchaseLog',hidden:true}
	 		 ]] ,
	 		 pagination:true,
	 		 pageList:[5,10,20],
	 		 fitColumns:true,
	 		 striped:true,
	 		 rownumbers:true,
	 		 singleSelect:true
	 		 });   
		
		doSearch();	
		
		// 双击查看详情
	
		
	
		var pager = $('#dg').datagrid('getPager');	
		pager.pagination({
			pageSize:10,
			pageNumber:1,
			showPageList:true,
			onSelectPage:function(pageNumber, pageSize){
				$.ajax({
					url:$UniformResourceLocation+'purchase/dynamicGet',
					dataType:'json',
					async:false,
					data:{						
						purchaseId: $purchaseId,
						purchaseBatch		:$purchaseBatch,
						supplier		:$supplier,
						employee		:$employee,
						startDate		:$startDate,
						endDate	:$endDate,
						purchaseStatus			:$purchaseStatus,
						currentPage			:pageNumber,
						pageSize			:pageSize,
					}, 
					success:function(data){
						transferStatus(data.value.rows);
						transferEmp(data.value.rows);
						transferSupplier(data.value.rows) ;
						$("#dg").datagrid('loadData',data.value);
						console.log(data);
					}
				});		
			}			
		});
	});	
	

	
	$(function(){
//		设置查询时间格式
		$('#startDate').datebox({
		    onSelect: function(date){
		        $startDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    }
		});	
		$('#endDate').datebox({
		    onSelect: function(date){
		        $endDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    }
		});	
//查询选框中数据
		$('#searchBatchC').combobox({
			url:$UniformResourceLocation+'purchase/getBatch',
			valueField : 'id',
			textField : 'text',
			onSelect : function(record) {
				$purchaseBatch = record.text;
			}
		});

	})
	function transferStatus(rows) {
		$.each(rows, function(i, item) {
			switch (item["purchaseStatus"]) {
			case 0:
				item["purchaseStatus"] = "已取消";
				break;
			case 1:
				item["purchaseStatus"] = "计划中";
				break;
			case 2:
				item["purchaseStatus"] = "进行中";
				break;
			case 3:
				item["purchaseStatus"] = "待入库";
				break;
			case 4:
				item["purchaseStatus"] = "已完成";
				break;
			default:
				break;
			}
		})
	}

	function transferEmp(rows) {
		$.each(rows, function(i, item) {
			item["employee"] = item["employee"].empName;
		})
	}
	
	function transferSupplier(rows) {
		$.each(rows, function(i, item) {
			item["supplier"] = item["supplier"].supplierName;
		})
	}



</script>

<title>采购管理</title>

</head>
<body>

<div>

</div>
	<table id="dg" title="采购信息" class="easyui-datagrid" onClickRow="ClickRow" toolbar="#toolBar" >
		
	</table>
	<div>

<span id="tips" style=“botton:380px;left:800px;”> </span>

</div>
	<div id="toolBar">
	&emsp;
<span>查询条件：</span>
	
	&ensp;
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
			&ensp;<input
			id="endDate" type="text" class="easyui-datebox" style="width: 100px" />
			&emsp;
		
			&emsp;
		<button  id="searchButton" onclick="doSearch()"  style="width: 100px" >开始查询</button>
		
		
	</div>

	

	
	
	 <div id="purchasePro" class="easyui-dialog" style="padding: 10px 20px; background:rgba(128,128,128,0.3)" closed="true" modal="true" maximizable="true">	
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
    <table id="dgPro" title="采购清单" class="easyui-datagrid">
    </table>
    </div>  
	<div id="toolBarPro">
	</div>
    </div>
    
        

	
	
	
	
	
</body>

	
</html>