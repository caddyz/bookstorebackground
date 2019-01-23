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
<link rel="stylesheet" type="text/css" href="<%=path %>/jquery-easyui-1.4.5/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/jquery-easyui-1.4.5/demo/demo.css">
<script type="text/javascript" src="<%=path %>/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=path %>/js/emp/userAction.js?v=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=path %>/js/myValidate.js"></script>
<script type="text/javascript" src="/bookstore/js/validateDemo.js"></script>
<title>员工页面</title>
<style type="text/css">
body{
	background-size: cover;
	background-repeat:no-repeat;
}

table{
	opacity : 0.7;
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

.fitem label {
	display: inline-block;
	width: 80px;
}
.easyui-numberbox{
	width: 75px;
}
</style>

<script type="text/javascript"> 
//全局变量

	
	function doSearch(value,name){ 
		// 每次查询返回到第一页
		setFirstPage("#dg");
		$.ajax({			
			url:'<%=basePath%>attendance/page/1/10',
			dataType:'json',
			type:'post',
			async:false,
			success:function(data){
				// 处理用户状态，将数值转为字符串
				getName(data.value.rows)
				$("#dg").datagrid('loadData',data.value);
			},
			error:function(msg){
				console.log(msg);
			}
		});
	}
	
	// 处理员工姓名、操作者姓名
	function getName(rows){
		$.each(rows,function(i, item){
			item.empName=item.emp.empName;
			item.operatorName=item.operator.emp.empName;
			item.primaryId=item.attendanceId;
		})
	}
	
	
	$(function(){
		
		var pager = $('#dg').datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			pageSize:10,
			pageNumber:1,
			showPageList:true,
			onSelectPage:function(pageNumber, pageSize){
				
				/* $(this).pagination('loading'); */
				/* alert('pageNumber:'+pageNumber+',pageSize:'+pageSize); */
				$.ajax({
					url:'<%=basePath%>attendance/page/'+pageNumber+'/'+pageSize,
					dataType:'json',
					type:'post',
					async:false,
					success:function(data){
						// 处理用户状态，将数值转为字符串
						getName(data.value.rows)
						$("#dg").datagrid('loadData',data.value);
					}
				});
				
			}
			
		});
	});
	
	$(function(){
		$('#excel').form({    
		    url:"<%=basePath%>attendance/upload",    
		       
		    success:function(data){ 
		    	var data = eval('(' + data + ')');
		    	if(!data["state"]){
		    		console.log(data)
		    		console.log(data.state);
		    		$.messager.alert('失败','文件格式是否规范呢？')
		    	}
		    },
		    error:function(err){
		    	console.log(err)
		    }
		});
		
		$('#file').filebox({    
		    accept:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel'
		})
	})
</script>

</head>
<body>
	<!-- pagination : 是否可以分页  url：对应一个handler来获取数据 toolbar：使用一个div与之对应 ；
	rownumbers：在数据展示的时候是否显示行数；fitColumns：数据显示时是否根据内容调整每一列的占位；singleSelect：选择行数时是否只能单行选择-->
	<table id="dg" title="考勤信息" class="easyui-datagrid" style="width: 100%; height: 100%" pagination="true" 
		    toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<!-- field ： 必须与返回json数据的字段(key)完全一致  ：json：total->总记录数 ;rows:数据-->
				<th field="primaryId" width="50">考勤编号</th>
				<th field="empName" width="50">员工姓名</th>
				<th field="workDay" width="50">出勤天数</th>
				<th field="leaveDay" width="50">请假天数</th>
				<th field="lateDay" width="50">迟到天数</th>
				<th field="earlyDay" width="50">早退天数</th>
				<th field="attDate" width="50">操作日期</th>
				<th field="operatorName" width="50">操作者</th>
				<!-- <th field="job" width="50">职位</th> -->

				
			</tr>
		</thead>
	</table>
	
	<div id="toolbar">
		<a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch()">查询考勤信息</a>  
		<a href="/bookstore/download/excel$$考勤表的模板.xlsx" class="easyui-linkbutton" data-options="iconCls:'icon-print'">下载Excel模板</a> 
		<!-- 上传考勤表的表单 -->
		<form id="excel" method="post" enctype="multipart/form-data">   
			<input id="file" class="easyui-filebox" name="file" style="width:300px" buttonText="选择本地考勤表" data-options="prompt:'请选择Excel文件'">	
			<input type="submit" value="确认上传">
			<input type="reset" value="重置">
		</form> 
		<!-- 下载考勤表模板 --> 
	</div>
	
	
	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">职位信息</div>
		<form id="fm" method="post" >
			<div class="fitem">
				<label>职位id:</label>
				<input name="jobId" type="text" class="easyui-numberbox" readonly="readonly"/>
			</div>
		
			<div class="fitem">
				<label>职位名称:</label>
				<input name="jobName" class="easyui-textbox" data-options="required:true">
			</div>
			<div class="fitem">
				<label>职位级别:</label>
				<input name="jobLevel" class="easyui-numberbox" data-options="required:true" >
			</div>
			
			<div class="fitem">
				<label>职位津贴:</label>
				<input name="jobAllowance" class="easyui-numberbox" data-options="required:true" >
			</div>
			
			<div class="fitem">
				<label>职位基本薪资:</label>
				<input name="jobBasicPay" class="easyui-numberbox" data-options="required:true" >
			</div>
			
			
			
			
		</form>
	</div>
	
	
</body>


</html>