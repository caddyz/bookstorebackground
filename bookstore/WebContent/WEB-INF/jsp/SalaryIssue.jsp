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
<script type="text/javascript" src="<%=path %>/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=path %>/js/activity/userAction.js"></script>
<script type="text/javascript" src="<%=path %>/js/myValidate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validateDemo.js"></script>
<title>工资发放</title>
<script type="text/javascript">
function doSearch(){
	$.ajax({			
		url:'<%=basePath %>financial/getSalaryData/1/10',
		dataType:'json',
		async:false,
		success:function(data){
			changeData(data.rows);
			$("#dg").datagrid('loadData',data);
		},
	});
}
$(function(){
	doSearch();
	var pager = $('#dg').datagrid('getPager');	
	pager.pagination({
		pageSize:10,
		pageNumber:1,
		showPageList:true,
		onSelectPage:function(pageNumber, pageSize){
			$.ajax({
				url:'<%= basePath%>financial/getSalaryData/'+pageNumber+'/'+pageSize,
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
		item.primaryId = item.salaryId;
		item.empName   = item.employee.empName;
		item.countMoney = parseInt(item.employee.job.jobBasicPay) + parseInt(item.employee.job.jobAllowance) + parseInt(item.other);
		item.salaryStatus = item.salaryStatus == false?"未发放":"已发放";
	})
}

</script>
<body>
<table id="dg" title="工资信息" class="easyui-datagrid" style="width: 100%; height: 100%" pagination="true" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="false">
	<thead>
			<tr>
				<th field="ck" data-options="checkbox:true"></th>
				<th data-options="field:'primaryId',width:30">薪资编号</th>
				<th data-options="field:'empName',width:30">员工姓名</th>
				<th data-options="field:'countMoney',width:30">工资金额</th>
				<th data-options="field:'salaryDate',width:30">发放日期</th>
				<th data-options="field:'salaryStatus',width:30">发放状态</th>
			</tr>
		</thead>
</table>
<div id="toolbar">
	<a href="#" rel="external nofollow" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="addSalary()">确认发放</a>
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
</body>
<script type="text/javascript">
function addSalary() {
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
		datas.id = rows[i].primaryId;
		datas.name = rows[i].empName;
		datas.money = rows[i].countMoney;
		datas.date = rows[i].salaryDate;
		datas.status = rows[i].salaryStatus;
		arr.push(datas);
		 var rowIndex = $('#dg').datagrid('getRowIndex', rows[i]);
		 // 把行号添加到集合
		 $index.push(rowIndex);
	}
	if(arr.length==0){
		$.messager.alert("提示", "请选择工资记录", "info");
		return;
   	}else{
   	}
	$.messager.confirm('提示', '是否发放选择工资', function (r) {
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
			url : '<%= basePath%>financial/addSalaryExpend',
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
	                $.messager.alert("提示", "工资发放成功", "info");
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