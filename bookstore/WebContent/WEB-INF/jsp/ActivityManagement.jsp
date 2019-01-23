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
<title>营销活动管理</title>
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
let $bookName  = '';
let $activityName  = '';
let $activityDiscount = '';
let $activityStart = '';
let $activityEnd = '';
function doSearch(){
	setFirstPage("#dg");
	$.ajax({			
		url:'<%=basePath %>activity/allActivityBook/1/10',
		dataType:'json',
		async:false,
		data:{
			bookName			:$bookName,
			activityName		:$activityName,
			activityDiscount	:$activityDiscount,
			activityStart		:$activityStart,
			activityEnd			:$activityEnd,
		},
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
				url:'<%= basePath%>activity/allActivityBook/'+pageNumber+'/'+pageSize,
				dataType:'json',
				async:false,
				data:{						
					bookName			:$bookName,
					activityName		:$activityName,
					activityDiscount	:$activityDiscount,
					activityStart		:$activityStart,
					activityEnd			:$activityEnd,
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
function doSearchButton() {
	doSearch();
}
$(function(){
	$('#activityStart').datebox({
	    onSelect: function(date){
	        $activityStart = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	    }
	});	
	$('#activityEnd').datebox({
	    onSelect: function(date){
	        $activityEnd = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	    }
	});		
	$('.ccbookName').combobox({
		url:'<%=basePath %>activity/allBookName',    
	    valueField:'text',    
	    textField:'text',
		onSelect: function(record){
			$bookName = record.text;
		}
	});		
	$('.ccactivityName').combobox({    
	    url:'<%=basePath %>activity/allActivityName',    
	    valueField:'text',    
	    textField:'text',
	    onSelect: function(record){
			$activityName = record.text;
		}
	});		
})
// 清空输入框
function clearButton() {
		$("input").val("");
		$activityName  = '';
		$activityStart = '';
		$activityEnd = '';
		$bookName  = '';
}
// 定义行号数组
var arr = [];
// 批量删除
function delActivity(){
	 var rows = $('#dg').datagrid('getSelections');
	 for(var i =0;i<rows.length;i++){
		 // 获取行号,
		 var rowIndex = $('#dg').datagrid('getRowIndex', rows[i]);
		 // 把行号添加到集合
		 arr.push(rowIndex);
	 }
	if (rows.length == 0) {
		   $.messager.alert("提示", "请选择要下架的活动！", "info");
			return;
	   }else{
	   }
	$.messager.confirm('提示', '是否下架选中活动?', function (r) {  		  
	    if (!r) {
	    	// 取消删除后,清空数组
	    	arr = [];
	    	// 取消选中
	    	cancel();
	        return;  
	    }
	    // 将数组中的行号排序
	    arr.sort(sortNumber);
	    // 提交
	    $.ajax({  
	    	type : "post",
			url : '<%= basePath%>activity/delActivity',
			contentType :"application/json",
			data :JSON.stringify(rows), 
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
	                arr = [];
	                $('#dg').datagrid('reload');
	                $.messager.alert("提示", "恭喜您，活动下架成功！", "info");  
	            } else {  
	                $.messager.alert("提示", "下架失败，请重新操作！", "info");
	                cancel();
	                return;  
	            }  
	        }  
	    });  
	});
} 
// 批量编辑   
function saveActivity() {
	if (endEditing()) {
		// 获取改变
		var updated = $('#dg').datagrid('getChanges',"updated");
		if(updated.length==0){
			 $.messager.alert("提示", "请选择要修改的行！", "info");
				return;
		   }else{
		   }
		$.messager.confirm('提示', '是否保存编辑的数据?', function (r) {  		  
		    if (!r) {
		    	cancel();
		        return;  
		    }
			// 提交
			$.ajax({
				url:'<%= basePath%>activity/updateActivity',
				dataType:'json',
				type:'POST',
				async: true,
				contentType:'application/json',
				data:JSON.stringify(updated),
				success:function(result){
					if (result.state=true) {
						$('#dg').datagrid('reload');
		                $.messager.alert("提示", "恭喜您，修改成功！", "info");  
		            } else {
		            	cancel();
		                $.messager.alert("提示", "修改失败，请重新操作！", "info");  
		                return;  
		            }	
				}
			});
		});
	}
}	  
</script>
</head>
<body>
	<!-- data-options="iconCls:'icon-edit',onDblClickCell:onDblClickCell, onEndEdit:onEndEdit" -->
	<table id="dg" title="活动信息" class="easyui-datagrid" style="width: 100%; height: 100%" pagination="true" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="false">
		<thead>
			<tr>
				<th field="ck" data-options="checkbox:true"></th>
				<th data-options="field:'bookName',width:30">书名</th>
				<th data-options="field:'activityName',width:30,
		   			formatter:function(value,row){
						return row.activityName;
					},
					editor:{
						type:'combobox',
						options:{
							valueField:'text',
							textField:'text',
							url:'<%= basePath%>activity/allActivityName', 
							method:'post',
						    required:true
						}
					}">活动名字</th>
				<th data-options="field:'activityDiscount',width:30,editor:{type:'numberbox'}">活动折扣</th>
				<th data-options="field:'activityStart',width:30,editor:{type:'datebox'}">开始时间</th>
				<th data-options="field:'activityEnd',width:30,editor:{type:'datebox'}">结束时间</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"onclick="doSearchButton()" style="width: 60px">查询</a>
	   	书名<input class="ccbookName" class="easyui-combobox" name="bookName" style="width:100px"/>
	      活动名<input class="ccactivityName" class="easyui-combobox" name="activityName" style="width:100px"/>
	  开始时间<input id="activityStart"  type= "text" class= "easyui-datebox" style="width:100px"/>
	  结束时间<input id="activityEnd"  type= "text" class= "easyui-datebox" style="width:100px"/>
	  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"onclick="clearButton()" style="width: 60px">清空</a>
		<br>	
		<a href="<%= basePath %>activity/create" rel="external nofollow" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建活动</a>
		<a href="#" rel="external nofollow" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delActivity()">活动下架</a>
	<!--<a href="#" rel="external nofollow" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveActivity()">保存</a>
		<a href="#" rel="external nofollow" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelActivity()">取消</a> -->
			
	</div>
	<div id="searchMenu" style="width:120px">
		<div></div>
	</div>
	 <div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
	 	<div></div>
	</div> 
	<div>
		<form id="create" method="post" >
		<div></div>
		</form>
	</div>
	<div id="dlg-buttons">
		<div></div>
	</div>
</body>
</html>