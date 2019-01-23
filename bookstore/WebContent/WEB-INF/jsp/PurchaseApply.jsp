<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<%=path %>/jquery-easyui-1.4.5/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/jquery-easyui-1.4.5/demo/demo.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/wsk.css">
<script type="text/javascript" src="<%=path %>/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=path%>/js/purchase/purchaseItemAction.js"></script>
<script type="text/javascript" src="<%=path %>/js/activity/userAction.js"></script>
<script type="text/javascript" src="<%=path %>/js/myValidate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validateDemo.js"></script>
<title>采购申请</title>
<script type="text/javascript">
let $UniformResourceLocation='http://'+window.location.host+'/bookstore/';
function doSearch(){
	$.ajax({			
		url:'<%=basePath %>financial/getPurchaseData/1/10',
		dataType:'json',
		async:false,
		success:function(data){
			changeData(data.rows);
			$("#dg").datagrid('loadData',data);
		},
	});
}
$(function(){
	
	$("#dg").datagrid({  
		   onDblClickRow: function (index, row){
				 purchaseProFun(row);	
		   } });
	
	doSearch();
	var pager = $('#dg').datagrid('getPager');	
	pager.pagination({
		pageSize:10,
		pageNumber:1,
		showPageList:true,
		onSelectPage:function(pageNumber, pageSize){
			$.ajax({
				url:'<%= basePath%>financial/getPurchaseData/'+pageNumber+'/'+pageSize,
				dataType:'json',
				async:false,
				data:{						
					pageNumber			:pageNumber,
					pageSize			:pageSize
				}, 
				success:function(data){
					changeData(data.rows);
					$("#dg").datagrid('loadData',data);		
				}
			});		
		}			
	});
});
function changeData(rows){
	$.each(rows, function(i, item){
		item.supplier = item.supplier.supplierName;
		item.purchaseStatus = "申请中";
	})
}
</script>
<body>
<table id="dg" title="采购申请信息" class="easyui-datagrid" style="width: 100%; height: 100%" pagination="true" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="false">
	<thead>
			<tr>
				<th field="ck" data-options="checkbox:true"></th>
				<th data-options="field:'purchaseId',width:30">采购编号</th>
				<th data-options="field:'purchaseBatch',width:30">采购批次</th>
				<th data-options="field:'supplier',width:30">供应商</th>
				<th data-options="field:'totalPay',width:30">采购金额</th>
				<th data-options="field:'purchaseDate',width:30">采购日期</th>
				<th data-options="field:'purchaseStatus',width:30">采购状态</th>
			</tr>
		</thead>
</table>
<div id="toolbar">
	<a href="#" rel="external nofollow" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="addPurchase()">确认信息</a>
</div>
<div id="searchMenu" style="width:120px">
	<div></div>
</div>
<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
	<div></div>
</div>
<div id="dlg-buttons">
	<div></div>
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
<script type="text/javascript">
function addPurchase() {
	// 定义传输对象
	var datas = null;
	// 定义传输数组
	var arr = [];
	var $index = [];
	// 获取选中行的数据
	var rows = $('#dg').datagrid('getSelections');
	// 遍历添加
	for(var i =0;i<rows.length;i++){
		datas = new Object();
		datas.id = rows[i].purchaseId;
		datas.count = rows[i].purchaseBatch;
		datas.name = rows[i].supplier;
		datas.money = rows[i].totalPay;
		datas.date = rows[i].purchaseDate;
		datas.status = rows[i].purchaseStatus;
		arr.push(datas);
		 var rowIndex = $('#dg').datagrid('getRowIndex', rows[i]);
		 // 把行号添加到集合
		 $index.push(rowIndex);
	}
	if(arr.length==0){
		$.messager.alert("提示", "请选择采购信息", "info");
		return;
   	}else{
   	}
	$.messager.confirm('提示', '确认操作选中数据？', function (r) {
		if (!r) {
	    	// 取消删除后,清空数组
	    	arr = [];
	    	$index = [];
	    	// 取消选中
	    	cancel();
	        return;  
	    }
		$.ajax({
			type : "post",
			url : '<%= basePath%>financial/addPurchaseExpsnd',
			contentType :"application/json",
			data :JSON.stringify(arr), 
			dataType : "json",
			cache : false,
			async : false,
			timeout : 60000,
			success: function (result) {  
	            if (result.state=true) {
	            	// 清空数组
	                arr = [];
	                for(var i=$index.length-1;i>=0;i--){
	                	 $('#dg').datagrid('deleteRow',$index[i]);
	                }
	                $.messager.alert("提示", "恭喜您，操作成功", "info");
	            } else {  
	                $.messager.alert("提示", "操作失败，请重新操作！", "info");
	                cancel();
	                return;  
	            }  
	        }           
		});
	});
}
</script>
</html>