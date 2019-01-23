<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<%=path%>/jquery-easyui-1.4.5/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/jquery-easyui-1.4.5/demo/demo.css">
<script type="text/javascript" src="<%=path%>/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=path%>/js/orderAction.js?a<%=Math.random() %>>"></script>
<script type="text/javascript" src="<%=path%>/js/setFirstPage.js?a<%=Math.random() %>>"></script>
<script type="text/javascript" src="<%=path%>/js/myValidate.js"></script>
<script type="text/javascript" src="<%=path%>/js/validateDemo.js"></script>
<script type="text/javascript" src="<%=path%>/js/address.js"></script>

<title>订单管理</title>

<style type="text/css">
body{
	background-color: #e3e3e3;
}

#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	color: #666;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.easyui-datagrid {
	width: 100%;
	height: 100%;
} 

.fitem label {
	display: inline-block;
	width: 80px;
}

.easyui-numberbox {
	width: 75px;
}

#orderStatus{
	width: 75px;
}

#dg, thead, tr{
	text-align: center;
}

#province, #citys, #county{
	width: 75px;
}
</style>

<script type="text/javascript"> 

	let $orderId = "";
	let $orderStatus = "";
	let $bottomPrice = "";
	let $topPrice = "";
	
	function doSearch(value, name){ 
		
			setFirstPage("#dg");
		
			$bottomPrice = $("#bottomPrice").val();
			$topPrice = $("#topPrice").val();
			$orderId = value;
			
			$.ajax({
				url:'<%=basePath%>orderController/getOrder/1/10',
				dataType : "json",
				async : false,
				data : {
					orderId : $orderId,
					orderStatus : $orderStatus,
					topPrice : $topPrice,
					bottomPrice : $bottomPrice,
				},
				success : function(data) {
					updateStatus(data.value.rows);
					$("#dg").datagrid('loadData', data.value);
				}
		});
	}

	// 分页的操作
	$(function() {
		var pager = $('#dg').datagrid('getPager'); // get the pager of datagrid
		pager.pagination({
			showPageList : true,
			onSelectPage : function(pageNumber, pageSize) {
				// $(this).pagination('loading'); // 定义数据是否正在加载

				$.ajax({
					url : '<%=basePath%>orderController/getOrder/'+pageNumber+'/'+pageSize,
				dataType : 'json',
				async : false,
				data : {
					orderId 			: 	$orderId,
					orderStatus	:	$orderStatus,
					topPrice 		: 	$topPrice,
					bottomPrice	: 	$bottomPrice,
				},
				success : function(data){
					updateStatus(data.value.rows);
					$("#dg").datagrid('loadData', data.value);
				}
			});
		}
	});
});
	
	// 清空选中项
	$(function(){
		$("#queryClear").bind('click', function(){
			$orderId = "";
			$orderStatus = "";
			$bottomPrice = "";
			$topPrice = "";
			$("input").val('');
		})
	})
	
	
	// 获取订单状态
	$(function(){
		$("#orderStatus").combobox({
			onSelect: function(record){
				$orderStatus = record.text;
			}
		})
		$('#orderStatus').combobox({    
		    url:'<%=basePath %>orderController/getOrderStatus',    
		    valueField:'id',    
		    textField:'text'   
		}); 
	})
	
	function formatCellTooltip(value){  
        return "<span title='双击查看详情'>" + value + "</span>";  
    }
	
</script>

</head>
<body>
	<!-- pagination : 是否可以分页  url：对应一个handler来获取数据 toolbar：使用一个div与之对应 ；
	rownumbers：在数据展示的时候是否显示行数；fitColumns：数据显示时是否根据内容调整每一列的占位；
	singleSelect：选择行数时是否只能单行选择-->
	<table id="dg" title="订单详情" class="easyui-datagrid" pagination="true" url="" toolbar="#toolbar" 
	rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="orderId" width="50" formatter="formatCellTooltip">订单号</th>
				<th field="expressName" width="30" formatter="formatCellTooltip">快递公司</th>
				<th field="userName" width="30" formatter="formatCellTooltip">用户名</th>
				<th field="addressConsignee" width="20" formatter="formatCellTooltip">收货人</th>
				<th field="mobile" width="40" formatter="formatCellTooltip">收货人电话</th>
				<th field="address" width="70" formatter="formatCellTooltip">地址</th>
				<th field="orderTime" width="50" formatter="formatCellTooltip">下单时间</th>
				<th field="orderStatus" width="30" formatter="formatCellTooltip">订单状态</th>
				<th field="totalPrice" width="15" formatter="formatCellTooltip">订单金额</th>
			</tr>
		</thead>
	</table>
		
	<div id="winInfo" class="easyui-window" title="购买的书" style="width:600px;height:400px"   
        data-options="iconCls:'icon-save', closed:true, modal:true">   
	    <table id="orderInfo" title="订单详情" class="easyui-datagrid" toolbar="" singleSelect="true">
			<thead>
				<tr>
					<th field="bookName" width="33.33%">书名</th>
					<th field="bookSalesPrice" width="33.33%">单价</th>
					<th field="bookNum" width="33.33%">数量</th>
				</tr>
			</thead>
		</table>
	</div> 
		
	<div id="toolbar">
		<a class="easyui-linkbutton" onclick="doSearch('', '')" data-options="iconCls:'icon-search'">查看所有订单</a>
		&nbsp;&nbsp;
		<!-- searcher：点击查询标识的时候要执行的方法 -->
		订单号<input id="ss" class="easyui-searchbox" style="width: 150px" data-options="searcher:doSearch,prompt:'请输入关键字'"></input>
		&nbsp;&nbsp; 
		价格区间<input id="bottomPrice" type="text" class="easyui-numberbox" value="" data-options="min:0,precision:2" />~
		 				<input id="topPrice" type="text" class="easyui-numberbox" value="" data-options="min:0,precision:2" /> &nbsp;&nbsp; 
		订单状态<input id="orderStatus" name="dept" value="">  &nbsp;&nbsp; 
		 <a id="queryClear" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">清空查询条件</a> 
		 <br/>

		<!-- 按键 -->
		<a class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editOrder()">修改订单</a> 
		<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeOrder()">删除订单</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="salesMessage()">确认退货</a>
	</div>
	
	<!-- 修改时弹出的框 -->
	<div id="dlg" class="easyui-dialog" style="width: 460px; height: 380px; padding: 10px 20px" closed="true" 
	buttons="#dlg-buttons">
		<div class="ftitle">修改订单信息</div>
		
		<form id="fm" method="post">
			<div class="fitem" >
				<label>订单号:</label>
				<input name="orderId" class="easyui-validatebox" data-options="required:true,validType:'number'">
			</div>

			<div class="fitem">
				<label>快递公司:</label> 
				<input name="expressName" class="easyui-validatebox" data-options="validType:'onlyChinese[1,20]'">
			</div>

			<div class="fitem">
				<label>收货人:</label> 
				<input name="addressConsignee" class="easyui-validatebox" >
			</div>
			
			<div class="fitem">
				<label>收货人电话:</label> 
				<input name="mobile" class="easyui-validatebox" data-options="validType:'number'">
			</div>
			
			<div class="fitem">
				<label>收货地址:</label> 
				<select id="province" name="province" onchange="doProvAndCityRelation()">
					<option id="choosePro"></option>
				</select>
				<select id="citys" name="citys" onchange="doCityAndCountyRelation()">
					<!-- <option id="chooseCity"></option> -->
				</select> 
				<select id="county" name="county">
					<!-- <option  id="chooseCounty"></option> -->
				</select> 
			</div>
			
			<div class="fitem">
				<label>详细地址:</label> 
				<input id="detail" name="detail" class="easyui-validatebox" >
			</div>
			
			<div class="fitem">
				<label>订单总金额:</label> 
				<input name="totalPrice" class="easyui-validatebox" data-options="validType:'number'">
			</div>
			
			<div class="fitem">
				<label>订单状态:</label>
				<input id="os1" name="orderStatus1" type="radio" value="1">待发货
				<input id="os2" name="orderStatus1" type="radio" value="2" >已发货
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<!-- Save 保存 -->
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOrder()">确认</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
</body>

</html>