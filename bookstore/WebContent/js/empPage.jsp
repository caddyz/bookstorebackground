<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<%=path %>jquery-easyui-1.4.5/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path %>jquery-easyui-1.4.5/demo/demo.css">
<script type="text/javascript" src="<%=path %>js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="<%=path %>jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript" src="<%=path %>jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="<%=path %>jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=path %>js/userAction.js"></script>
<script type="text/javascript" src="<%=path %>js/myValidate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validateDemo.js"></script>
<title>员工管理操作页面</title>
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
	let $date1 = '';
	let $date2 = '';
	let $kind  = '';
	let $name  = '';
	let $value = '';
	let $bottomPrice = '';
	let $topPrice = '';
	function doSearch(value,name){ 
		$name  		  = name;
		$value 		  = value;
		$bottomPrice  = $("#bottomPrice").val();
		$topPrice 	  = $("#topPrice").val();
		
		$.ajax({			
			url:'BookSearchServlet?pageNum=1&pageSize=10',
			dataType:'json',
			async:false,
			data:{
				name		:$name,
				value		:$value,
				bottomPrice	:$bottomPrice,
				topPrice	:$topPrice,
				date1		:$date1,
				date2		:$date2,
				kind		:$kind
			},
			success:function(data){
				$("#dg").datagrid('loadData',data);
				
			}
		});
	} 
	
	
	$(function(){
		
		var pager = $('#dg').datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			pageSize:10,
			pageNumber:2,
			showPageList:true,
			onSelectPage:function(pageNumber, pageSize){
				
				/* $(this).pagination('loading'); */
				/* alert('pageNumber:'+pageNumber+',pageSize:'+pageSize); */
				$.ajax({
					url:'BookSearchServlet',
					dataType:'json',
					async:false,
					data:{
						
						name		:$name,
						value		:$value,
						bottomPrice	:$bottomPrice,
						topPrice	:$topPrice,
						date1		:$date1,
						date2		:$date2,
						kind		:$kind,
						pageNum		:pageNumber,
						pageSize	:pageSize
					}, 
					success:function(data){
						$("#dg").datagrid('loadData',data);
					}
				});
				
			}
			
		});
	});
	
	$(function(){
		$('#date1').datebox({
		    onSelect: function(date){
		        $date1 = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    }
		});
		
		$('#date2').datebox({
		    onSelect: function(date){
		        $date2 = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    }
		});

		$('#cc').combobox({
			onSelect: function(record){
				$kind = record.text;
			}
		});
		
		$('#cckind').combobox({
			onSelect: function(record){
				$kind = record.text;
			}
		});


		 



		 

	})
</script>
</head>
<body>
	<!-- pagination : 是否可以分页  url：对应一个handler来获取数据 toolbar：使用一个div与之对应 ；
	rownumbers：在数据展示的时候是否显示行数；fitColumns：数据显示时是否根据内容调整每一列的占位；singleSelect：选择行数时是否只能单行选择-->
	<table id="dg" title="用户信息" class="easyui-datagrid" style="width: 100%; height: 100%" pagination="true" 
		   url="1/10/getUsers.do" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<!-- field ： 必须与返回json数据的字段(key)完全一致  ：json：total->总记录数 ;rows:数据-->
				<th field="bookId" width="50">书号</th>
				<th field="bookName" width="50">书名</th>
				<th field="author" width="50">作者</th>
				<th field="price" width="50">价格</th>
				<th field="publishDate" width="50">发布日期</th>
				<th field="kind" width="50">类型</th>
				<th field="totalStore" width="50">总库存量</th>
				<th field="totalSold" width="50">总销量</th>
				<th field="pic" width="50">封面</th>
				<th field="profile" width="50">简介</th>
				<th field="status" width="50">状态</th>
				
			</tr>
		</thead>
	</table>
	
	<div id="toolbar">
		<!-- searcher：点击查询标识的时候要执行的方法 -->
		<input id="ss" class="easyui-searchbox" style="width:300px" data-options="searcher:doSearch,prompt:'请输入查询关键字',menu:'#searchMenu'"></input> 
		价格：<input id="bottomPrice" type="text" class="easyui-numberbox" value="10" data-options="min:0,precision:2">  
		~<input id="topPrice" type="text" class="easyui-numberbox" value="100" data-options="min:0,precision:2">
		发行日期：<input  id="date1"  type= "text" class= "easyui-datebox" required ="required">   
		~<input  id="date2"  type= "text" class= "easyui-datebox" required ="required">   
		类型：
		<input id="cc" name="dept" value="">  

		

		<br>

		
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New Book</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit Book</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">Remove Book</a>
		
		<form id="excelUp" enctype="multipart/form-data" method="post" action="UploadExcelServlet" enctype="multipart/form-data">   
			<!--  <input id="excelUp" class="easyui-filebox" multiple="multiple" style="width:300px" name="file" buttonText="选择excel文件">-->
			<input type="file" name="file" multiple="multiple">
			<input type="submit" value="提交">
		</form> 
	</div>
	<div id="searchMenu" style="width:120px"> 
		<div data-options="name:'bookName',iconCls:'icon-ok'">书名</div>
		<div data-options="name:'bookId'">书号</div>
		<div data-options="name:'author'">作者</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">图书信息</div>
		<!-- <form id="fm" method="post" novalidate onsubmit="return abc()">  onsubmit是真正提交之前执行的方法，表单是否能够提交是以它的返回值为准true就能提交-->
		<form id="fm" method="post" >
			<div class="fitem">
				<label>ID:</label> 
				<input name="id" class="easyui-textbox" data-options="readonly:true" >
			</div>
			<!-- <div class="fitem">
				<label>书号:</label> 
				<input name="bookId"type="text" class="easyui-numberbox" value="" data-options="min:0,precision:2">
			</div> -->
			<div class="fitem">
				<label>书名:</label>
				<input name="bookName" class="easyui-validatebox" data-options="required:true">
			</div>
			<div class="fitem">
				<label>作者:</label>
				<input name="author" class="easyui-validatebox" data-options="required:true" >
			</div>
			<div class="fitem">
				<label>价格:</label> 
				<input name="price" type="text" class="easyui-numberbox" data-options="min:0,precision:2"></input>  
			</div>
			<div class="fitem">
				<label>发布日期:</label> 
				<input name="publishDate" type= "text" class= "easyui-datebox" required ="required"> 
			</div>
			<div class="fitem">
				<label>类型:</label> 
				<input id="cckind" name="kind" value="aa" style="width: 50px"> 
			</div>
			<div class="fitem">
				<label>总库存量:</label> 
				<input name="totalStore" type="text" class="easyui-numberbox" data-options="min:0"></input>  
			</div>
			<div class="fitem">
				<label>总销量:</label> 
				<input name="totalSold"  type="text" class="easyui-numberbox" data-options="min:0">
			</div>
			<div class="fitem">
				<label>封面:</label> 
				<input name="pic" class="easyui-filebox" style="width:300px">
			</div>
			<div class="fitem">
				<label>简介:</label> 
				<input name="profile" class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:300px"> 
			</div>			
			<div class="fitem">
				<label>状态:</label> 
				<input name="status" class="easyui-validatebox" data-options="required:true,validType:['email','length[10,20]']">
			</div>
			
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"onclick="saveUser()">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>


<script type="text/javascript">
$(function(){
	$('#cc').combobox({    
	    url:'BookKindServlet',    
	    valueField:'id',    
	    textField:'text'   
	});
	
	$('#cckind').combobox({    
	    url:'BookKindServlet',    
	    valueField:'id',    
	    textField:'text'   
	});  




	
})





</script>
<script src="//open.sojson.com/common/js/canvas-nest.min.js" count="200" zindex="-2" opacity="0.5" color="47,135,193" type="text/javascript"> 
</script>
</html>