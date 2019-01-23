<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<title>历史活动记录</title>
<style type="text/css">
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
.fitem label {
	display: inline-block;
	width: 80px;
}
.easyui-numberbox{
	width: 75px;
}
</style>
<script type="text/javascript">
// 定义变量
function doSearch(){
	setFirstPage("#dg");
	$.ajax({			
		url:'<%=basePath %>activity/getHistoryActivity/1/10',
		dataType:'json',
		async:false,
		success:function(data){
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
				url:'<%= basePath%>activity/getHistoryActivity/'+pageNumber+'/'+pageSize,
				dataType:'json',
				async:false,
				data:{						
					pageNumber			:pageNumber,
					pageSize			:pageSize
				}, 
				success:function(data){
					$("#dg").datagrid('loadData',data);		
				}
			});		
		}			
	});
});
// 定义行号的集合
var arr = [];
// 启用历史活动
function addActivity() {
	// 获取选中行的数据
	var rows = $('#dg').datagrid('getSelections');
	// 循环遍历添加数组
	for(var i =0;i<rows.length;i++){
		 // 获取行号,
		 var rowIndex = $('#dg').datagrid('getRowIndex', rows[i]);
		 // 把行号添加到集合
		 arr.push(rowIndex);
	 }
	if(rows.length==0){
		 $.messager.alert("提示", "请选择要启用的行！", "info");
			return;
	   }else{
	   }
	$.messager.confirm('提示', '是否启用选中数据?', function (r) {  		  
	    if (!r) {
	    	// 取消删除后,清空数组
	    	arr = [];
	    	// 取消选中
	    	cancel();
	        return;  
	    }
	    // 将数组中的行号排序
	    arr.sort(sortNumber);
		$.ajax({
			type : "post",
			url : '<%= basePath%>activity/addActivity',
			contentType :"application/json",
			data :JSON.stringify(rows), 
			dataType : "json",
			cache : false,
			async : false,
			timeout : 60000,
		    success: function (result) {  
	            if (result.state=true) {
	            	// 从大到小删除行号
	                for(var i=arr.length-1;i>=0;i--){
	                	 $('#dg').datagrid('deleteRow',arr[i]);
	                }
	            	// 清空数组
	                arr = [];
	                $.messager.alert("提示", "恭喜您，活动重新启用成功！", "info");
	            } else {  
	                $.messager.alert("提示", "启用失败，请重新操作！", "info");  
	                return;  
	            }  
	        } 
		});
	});
}
// 定义数据对象
var activityBooks = null;
// 定义数据的集合
var datas = [];
//批量删除
function delActivity(){
	 var rows = $('#dg').datagrid('getSelections');
	 for(var i =0;i<rows.length;i++){
		 // 定义对象,将数据存入对象
		 activityBooks = new Object();
		 activityBooks.activityId = rows[i].activityId;
		 // 把对象添加到集合
		 datas.push(activityBooks);
		 // 获取行号
		 var rowIndex = $('#dg').datagrid('getRowIndex', rows[i]);
		 // 把行号添加到集合
		 arr.push(rowIndex);
	 }
	    if (rows.length == 0) {
			   $.messager.alert("提示", "请选择要删除的行！", "info");
				return;
		   }else{
		   }
		$.messager.confirm('提示', '是否删除选中数据?', function (r) {  		  
		    if (!r) {
		    	// 取消删除后,清空数组
		    	arr = [];
		    	datas = [];
		    	// 取消选中状态
		    	cancel();
		        return;  
		    }
		 	// 将数组中的行号排序
		    arr.sort(sortNumber);
	    // 提交
	    $.ajax({  
	    	type : "post",
			url : '<%= basePath%>activity/delHistory',
			contentType :"application/json",
			data :JSON.stringify(datas), 
			dataType : "json",
			cache : false,
			async : false,  
	        success: function (result) {  
	            if (result.state=true) {
	            	// 从大到小删除行号
	                for(var i=arr.length-1;i>=0;i--){
	                	 $('#dg').datagrid('deleteRow',arr[i]);
	                }
	            	// 清空数组
	                datas =[];
	                arr = [];
	                $.messager.alert("提示", "恭喜您，信息删除成功！", "info");  
	            } else {  
	                $.messager.alert("提示", "删除失败，请重新操作！", "info");  
	                return;  
	            }  
	        }  
	    });  
	});
} 
</script>
</head>
<body>
	<table id="dg" title="历史活动信息" class="easyui-datagrid" style="width: 100%; height: 100%" pagination="true" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="false">
		<thead>
			<tr>
				<th field="ck" data-options="checkbox:true"></th>
				<th field="activityId" width="50" editor="text">活动ID</th>
				<th field="bookId" width="50" editor="text">书ID</th>
				<th field="bookName" width="50" editor="text">书名</th>
				<th field="activityName" width="50" editor="text">活动名</th>
				<th field="activityDiscount" width="50" editor="text">活动折扣</th>
				<th field="activityStart" width="50" editor="text">活动开始时间</th>
				<th field="activityEnd" width="50" editor="text">活动结束时间</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" rel="external nofollow" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addActivity()">启用活动</a>
		<a href="#" rel="external nofollow" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delActivity()">彻底删除</a>
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
</html>