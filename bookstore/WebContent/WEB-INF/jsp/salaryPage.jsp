<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>jQuery EasyUI</title>
	<link rel="stylesheet" type="text/css" href="<%=path %>/jquery-easyui-1.4.5/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/jquery-easyui-1.4.5/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/jquery-easyui-1.4.5/demo/demo.css">
	<script type="text/javascript" src="<%=path %>/js/jquery-2.2.1.min.js"></script>
	<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
	<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
	<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
	<script type="text/javascript" src="<%=path %>/js/emp/userAction.js"></script>
	<script type="text/javascript" src="<%=path %>/js/myValidate.js"></script>
	<script type="text/javascript" src="<%=path %>/js/validateDemo.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery.edatagrid.js"></script>
	<script type="text/javascript">
	</script>
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
	let $date1  = '';
	let $date2  = '';
	function doSearch(value,name){ 
		
		// 每次查询返回到第一页
		setFirstPage("#dg");
		
		$.ajax({			
			url:'<%=basePath%>salary/page/1/10',
			dataType:'json',
			async:false,
			data:{
				date1	:$date1,
				date2	:$date2
			},
			success:function(data){
				changeRow(data.value.rows);
				$("#dg").datagrid('loadData',data.value);
			},
			error:function(msg){
				$.messager.alert('失败','输入是否规范呢？')
				console.log(msg);
			}
		});
	}
	
	$(function(){
		$('#date1').datebox({    
		    required:true   
		});   

		$('#date2').datebox({    
		    required:true   
		});   
		
		var pager = $('#dg').datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			pageSize:10,
			pageNumber:1,
			showPageList:true,
			onSelectPage:function(pageNumber, pageSize){
				
				$.ajax({
					url:'<%=basePath%>salary/page/'+pageNumber+'/'+pageSize,
					dataType:'json',
					async:false,
					data:{
						pageNum		:pageNumber,
						pageSize	:pageSize
					}, 
					success:function(data){
						changeRow(data.value.rows);
						$("#dg").datagrid('loadData',data.value);
					}
				});
				
			}
			
		});
		
		// 员工id下拉数据列表
		$('#empId').combogrid({    
		    panelWidth:450,    
		    value:'006',    
		     
		    idField:'empId',    
		    textField:'empName',    
		    url:'/bookstore/employee/allEmp',    
		    columns:[[    
		        {field:'empId',title:'员工编号',width:60},    
		        {field:'empName',title:'员工姓名',width:100},    
		        {field:'empDept',title:'员工部门',width:120},    
		    ]]    
		}); 
	});

	function changeRow(rows){
		$.each(rows,function(i, item){
		item.primaryId=item.salaryId;
		item.jobName=item.employee.job.jobName;
		item.empName=item.employee.empName;
		item.empId  =item.employee.empId;
		item.allowance=item.employee.job.jobAllowance;
		item.operatorName = item.operator.emp.empName;
		item.basicPay = item.employee.job.jobBasicPay;
		item.whole = parseInt(item.basicPay) + parseInt(item.allowance) + parseInt(item.other);
		item.salaryStatus = item.salaryStatus==true?"已发放":"未发放";
	})
	}
	
	// 添加工资信息后刷新所需要的处理全局变量$result
	function changeResult(){
		$result.value.primaryId = $result.value.salaryId;
		$result.value.jobName = $result.value.employee.job.jobName;
		$result.value.empName=$result.value.employee.empName;
		$result.value.empId  =$result.value.employee.empId;
		$result.value.allowance=$result.value.employee.job.jobAllowance;
		$result.value.operatorName = $result.value.operator.emp.empName;
		$result.value.basicPay = $result.value.employee.job.jobBasicPay;
		$result.value.whole = parseInt($result.value.basicPay) + parseInt($result.value.allowance) + parseInt($result.value.other);
		$result.value.salaryStatus = $result.value.salaryStatus==true?"已发放":"未发放";
	}
		
</script>
</head>

<body>
<button id="change" onclick="changeResult()" style="display: none"></button>
	<!-- pagination : 是否可以分页  url：对应一个handler来获取数据 toolbar：使用一个div与之对应 ；
	rownumbers：在数据展示的时候是否显示行数；fitColumns：数据显示时是否根据内容调整每一列的占位；singleSelect：选择行数时是否只能单行选择-->
	<table id="dg" title="薪资信息" class="easyui-datagrid" style="width: 100%; height: 100%" pagination="true" 
		    toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<!-- field ： 必须与返回json数据的字段(key)完全一致  ：json：total->总记录数 ;rows:数据-->
				<th field="primaryId" width="50">薪资编号</th>
				<th field="empId" width="50">员工编号</th>
				<th field="empName" width="50">员工</th>
				<th field="jobName" width="50">职位</th>
				<th field="salaryDate" width="50">发放日期</th>
				<th field="basicPay" width="50">底薪</th>
				<th field="allowance" width="50">职位津贴</th>
				<th field="other" width="50">其他（奖罚）</th>
				<th field="whole" width="50">合计</th>
				<th field="operatorName" width="50">操作人</th>
				<th field="salaryStatus" width="50">发放状态</th>
			</tr>
		</thead>
	</table>
	
	<div id="toolbar">
		<!-- searcher：点击查询标识的时候要执行的方法 -->
		<a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch()">查询所有薪资信息</a>
		
		<br>

		
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="news('/bookstore/salary/add')">添加工资记录</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removes('/bookstore/salary/remove')">删除工资记录</a>
		
		
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">职位信息</div>
		<form id="fm" method="post" >
			<div class="fitem">
				<label>薪资编号:</label>
				<input name="primaryId" type="text" class="easyui-numberbox" readonly="readonly"/>
			</div>
			<div class="fitem">
				<label>员工:</label>
				<!-- <input name="empId" type="text" class="easyui-numberbox" data-options="required:true"/> -->
				<input id="empId" name="empId" style="width: 130px;" data-options="required:true"/> 
			</div>
			
			
			
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"onclick="saves()">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>