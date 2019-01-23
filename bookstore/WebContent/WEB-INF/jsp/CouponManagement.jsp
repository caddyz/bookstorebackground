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
<script type="text/javascript" src="<%=path %>/js/activity/couponUserAction.js"></script>
<script type="text/javascript" src="<%=path %>/js/myValidate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validateDemo.js"></script>
<title>优惠券管理</title>
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
let $couponName  = '';
let $couponStart = '';
let $couponEnd = '';
let $couponStatus = '';
function doSearch(){
	setFirstPage("#dg");
	$.ajax({			
		url:'<%=basePath %>coupon/allCoupon/1/10',
		dataType:'json',
		async:false,
		data:{
			couponName			:$couponName,
			couponStart			:$couponStart,
			couponEnd			:$couponEnd,
			couponStatus		:$couponStatus,
		},
		success:function(data){
			$("#dg").datagrid('loadData',data);
		}
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
				url:'<%= basePath%>coupon/allCoupon/'+pageNumber+'/'+pageSize,
				dataType:'json',
				async:false,
				data:{	
					couponName			:$couponName,
					couponStart			:$couponStart,
					couponEnd			:$couponEnd,
					couponStatus		:$couponStatus,
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
	// 开始日期
	$('#couponStart').datebox({
	    onSelect: function(date){
	        $couponStart = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	    }
	});	
	// 结束日期
	$('#couponEnd').datebox({
	    onSelect: function(date){
	        $couponEnd = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	    }
	});		
	// 优惠券名下拉框
	$('.ccCouponName').combobox({
		url:'<%=basePath %>coupon/allCouponName',    
	    valueField:'text',    
	    textField:'text',
		onSelect: function(record){
			$couponName = record.text;
		}
	});		
	// 类型下拉框
	$('.cccouponStatus').combobox({
		url:'<%=basePath %>coupon/allStatus',    
	    valueField:'text',    
	    textField:'text',
		onSelect: function(record){
			$couponStatus = record.text;
		}
	});		
})	 
// 清空输入框
function clearButton() {
	$("input").val("");
	$couponName  = '';
	$couponStart = '';
	$couponEnd = '';
	$couponStatus = '';
}
</script>
<script type="text/javascript">
function editCoupon() {
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
				url:'<%= basePath%>coupon/updateCoupon',
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
//定义行号数组
var arr = [];
var datas = [];
var coupon = null;
// 批量删除
function delCoupon(){
	 var rows = $('#dg').datagrid('getSelections');
	 for(var i =0;i<rows.length;i++){
		  coupon = new Object();
		  coupon.couponId = rows[i].couponId;
		  datas.push(coupon);
		 // 获取行号
		 var rowIndex = $('#dg').datagrid('getRowIndex', rows[i]);
		 // 把行号添加到集合
		 arr.push(rowIndex);
	 }
	if (datas.length == 0) {
		   $.messager.alert("提示", "请选择要删除的优惠券！", "info");
			return;
	   }else{
	   }
	$.messager.confirm('提示', '是否删除选中优惠券?', function (r) {  		  
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
			url : '<%= basePath%>coupon/delCoupon',
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
	                arr = [];
	            	datas = [];
	                $('#dg').datagrid('reload');
	                $.messager.alert("提示", "恭喜您，删除成功！", "info");  
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
	<table id="dg" title="优惠券信息" class="easyui-datagrid" style="width: 100%; height: 100%" pagination="true" toolbar="#toolbar" 
	rownumbers="true" fitColumns="true" singleSelect="false" data-options="iconCls:'icon-edit',onDblClickCell:onDblClickCell, onEndEdit:onEndEdit">
		<thead>
			<tr>
				<th field="ck" data-options="checkbox:true"></th>
				<th data-options="field:'couponName',width:30">优惠券名</th>
				<th data-options="field:'couponMoney',width:30">优惠金额</th>
				<th data-options="field:'couponNum',width:30,editor:{type:'numberbox'}">发放总数</th>
				<th data-options="field:'couponStart',width:30,editor:{type:'datebox'}">开始时间</th>
				<th data-options="field:'couponEnd',width:30,editor:{type:'datebox'}">结束时间</th>
				<th data-options="field:'couponRemainNum',width:30,editor:{type:'numberbox'}">剩余数量</th>
				<th data-options="field:'couponStatus',width:30">优惠类型</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"onclick="doSearchButton()" style="width: 60px">查询</a>
     优惠券名<input class="ccCouponName" class="easyui-combobox" name="couponName" style="width:100px"/>
     开始时间<input id="couponStart"  type= "text" class= "easyui-datebox" style="width:100px"/>
     结束时间<input id="couponEnd"  type= "text" class= "easyui-datebox" style="width:100px"/>
      类	   型<input class="cccouponStatus" class="easyui-combobox" name="couponStatus" style="width:100px"/>
      <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"onclick="clearButton()" style="width: 60px">清空</a>  		
		<br>	
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newCoupon()">添加优惠券</a>
		<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-nav" plain="true" onclick="editCoupon()">修改优惠券</a> -->
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delCoupon()">删除优惠券</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="editCoupon()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancel()">取消</a>		
	</div>
	<div id="searchMenu" style="width:120px">
		<div></div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">优惠券信息</div>
		<form id="fm" method="post" >
			<div class="fitem">
				<label>编号</label> 
				<input name="couponId" class="easyui-textbox" data-options="readonly:true" >
			</div>			
			<div class="fitem">
				<label>优惠券名</label>
				<input class="ccCouponName easyui-textbox" name="couponName" style="width:173px" required ="required"/>
			</div>
			<div class="fitem">
				<label>金额</label>
				<input name="couponMoney" type= "text" class="easyui-numberbox" data-options="min:0,precision:0" style="width:173px" required ="required"/>
			</div>
			<div class="fitem">
				<label>总数</label> 
				<input name="couponNum" type= "text" class="easyui-numberbox" data-options="min:0,precision:0" style="width:173px" required ="required"/>  
			</div>
			<div class="fitem">
				<label>开始日期</label> 
				<input name="couponStart" type= "text" class= "easyui-datebox" required ="required"> 
			</div>
			<div class="fitem">
				<label>结束日期</label> 
				<input name="couponEnd" type= "text" class= "easyui-datebox" required ="required"> 
			</div>	
			<div class="fitem">
				<label>剩余数量</label> 
				<input name="couponRemainNum" type="text" class="easyui-numberbox" data-options="min:0,precision:0" style="width:173px" required ="required"/>  
			</div>
			<div class="fitem">
				<label>类型</label> 
					<input class="cccouponStatus easyui-textbox" name="couponStatus" style="width:173px" required ="required"/>
			</div>						
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"onclick="saveCoupon()">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>