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
<link rel="stylesheet" type="text/css" href="<%=path%>/css/loadwait.css">
<script type="text/javascript" src="<%=path %>/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=path %>/js/emp/userAction.js"></script>
<script type="text/javascript" src="<%=path %>/js/myValidate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validateDemo.js"></script>
<script type="text/javascript" src="<%=path%>/js/loadwait.js"></script>
<title>后台账户页面</title>
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
	
	let $name  = '';
	let $value = '';
	let $method = '';
	let $adminEmpName;
	function doSearch(value,name){ 
		$name  		  = name;
		$value 		  = value;
		// 每次查询返回到第一页
		setFirstPage("#dg");
		$.ajax({			
			url:'<%=basePath%>admin/page/1/10',
			dataType:'json',
			async:false,
			data:{
				name		:$name,
				value		:$value,
			},
			success:function(data){
				// 将data中的员工对象转换成员工名字
				getEmpName(data.value.rows);
				$("#dg").datagrid('loadData',data.value);
			},
			error:function(msg){
				$.messager.alert('失败','输入是否规范呢？')
				console.log(msg);
			},
			beforeSend:function(){
	            /*请求发送前向body中添加进度等待蒙版，样式同上*/
	            $("body").append("<div class='loading'><div class='bigpic'></div></div>");
	        },
			complete:function(xhr){
	            /*数据加载完成后，将蒙版移除*/
	            $(".loading").fadeOut();
	        }
		});
	}
	
	
	
	$(function(){
		// 刚进入页面时展示所有账户
//		doSearch("","adminAccount");
		
		var pager = $('#dg').datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			pageSize:10,
			pageNumber:1,
			showPageList:true,
			onSelectPage:function(pageNumber, pageSize){
				
				/* $(this).pagination('loading'); */
				/* alert('pageNumber:'+pageNumber+',pageSize:'+pageSize); */
				$.ajax({
					url:'<%=basePath%>admin/page/'+pageNumber+'/'+pageSize,
					dataType:'json',
					async:false,
					data:{
						name		:$name,
						value		:$value,
						pageNum		:pageNumber,
						pageSize	:pageSize
					}, 
					success:function(data){
						// 处理员工姓名
						getEmpName(data.value.rows);
						$("#dg").datagrid('loadData',data.value);
					}
				});
				
			}
			
		});
		
		// 获取所有权限信息
		$.ajax({
			url:'<%=basePath%>admin/allPermission',
			dataType:'json',
			async:false,
			
			success:function(data){
				$.each(data,function(i,item){
					$("#permissions").append("<br><input name='permission' type='checkbox' class='permissionBox' value='"+item.permissonId +"'>"+item.permissionName)
				})
			}
		})
		
		// 员工id下拉数据列表
		$('#adminEmpId').combogrid({    
		    panelWidth:450,    
		    value:'006',    
		     
		    idField:'empId',    
		    textField:'empName',    
		    url:'/bookstore/employee/allEmp',    
		    columns:[[    
		        {field:'empId',title:'员工编号',width:60},    
		        {field:'empName',title:'员工姓名',width:100},    
		        {field:'empDept',title:'员工部门',width:120},    
		    ]],
		    onSelect:function(index,row){
		    	$adminEmpName = row.empName;
		    }
		});
		
		$("#adda").on('click',function(){
			$method = 'adda';
		});
		
		$("#upda").on('click',function(){
			$method = 'upda';
		});
		
		// 焦点离开登录帐号时，查询帐号是否重复
		$("#err").hide();
		$("[name='adminAccount']").blur(function(){
			var accdata = {};
			var accurl = '';	
			accdata.account = $("#adminAccount").val();
			if($method == 'adda'){
				accurl = '<%=basePath%>admin/sameAllAccount'
			}else if($method == 'upda'){
				accurl = '<%=basePath%>admin/sameOtherAccount'
				accdata.id = $("[name='primaryId']").val();
			}
			$.ajax({
				url : accurl,
				data : accdata,
				success:function(data){
					if(data == false){
						$("#err").show()
					}else{
						$("#err").hide()
					}
				}
			})
		})
		
		// 全选、全不选
		$("#checkAll").click(function(){
		    if($(this).prop("checked")){
		    	$('.permissionBox').prop("checked", true);
		    }else{
		    	$('.permissionBox').prop("checked", false);
		    }
		});

	});
	
		
	function getEmpName(rows){
		$.each(rows, function(i, item){
			item.adminEmpName = item.emp.empName;
			item.primaryId = item.adminId;
		})
	}
	
	// 添加账户后刷新所需要的处理全局变量$result
	function changeResult(){
		$result.value.primaryId = $result.value.adminId;
		$result.value.adminEmpName = $result.value.emp.empName;
	}
	
	 



</script>

</head>
<body>
<button id="change" onclick="changeResult()" style="display: none"></button>
	<!-- pagination : 是否可以分页  url：对应一个handler来获取数据 toolbar：使用一个div与之对应 ；
	rownumbers：在数据展示的时候是否显示行数；fitColumns：数据显示时是否根据内容调整每一列的占位；singleSelect：选择行数时是否只能单行选择-->
	<table id="dg" title="用户信息" class="easyui-datagrid" style="width: 100%; height: 100%" pagination="true" 
		    toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<!-- field ： 必须与返回json数据的字段(key)完全一致  ：json：total->总记录数 ;rows:数据-->
				<th field="primaryId" width="50">账户编号</th>
				<th field="adminAccount" width="50">登录帐号</th>
				<th field="adminPassWord" width="50">登录密码</th>
				<th field="adminEmpName" width="50">员工姓名</th>
			</tr>
		</thead>
	</table>
	
	<div id="toolbar">
		<!-- searcher：点击查询标识的时候要执行的方法 -->
		<input id="ss" class="easyui-searchbox" style="width:300px" data-options="searcher:doSearch,prompt:'请输入查询关键字',menu:'#searchMenu'"></input> 
		  
		
		<br>

		
		<a href="#" id="adda" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="news('/bookstore/admin/add')">添加账户</a>
		<a href="#" id="upda" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edits('/bookstore/admin/update')">修改账户</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removes('/bookstore/admin/remove')">删除账户</a>
		
		
	</div>
	<div id="searchMenu" style="width:120px"> 
		<div data-options="name:'adminAccount'">登录帐号</div>
		<div data-options="name:'adminId',iconCls:'icon-ok'">账户id</div>
		<div data-options="name:'empId'">员工id</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">后台账户信息</div>
		<form id="fm" method="post" >
			<div class="fitem">
				<label>账户id:</label>
				<input name="primaryId" type="text" class="easyui-numberbox" readonly="readonly"/>
			</div>
		
			<div class="fitem">
				<label>员工:</label>
				<!-- <input name="empId" class="easyui-numberbox" data-options="required:true"> -->
				<input id="adminEmpId" name="empId" style="width: 130px;" data-options="required:true"/> 
			</div>
			<div class="fitem">
				<label>登录帐号:</label>
				<input name="adminAccount" id="adminAccount" class="easyui-validatebox" data-options="required:true" ><span id="err" style="color:red;">帐号重复</span>
			</div>
			
			<div class="fitem">
				<label>登录密码:</label>
				<input name="adminPassWord" class="easyui-validatebox" data-options="required:true" >
			</div>
			
			<div class="fitem" id="permissions">
				<label>权限</label>
				<br>
				<input id="checkAll" type="checkbox">全选
				<!-- <input name="permission" class="easyui-validatebox" data-options="required:true" > -->
				<!-- <input id="permission" name="permission" type="text" style="width:300px"> -->
				
				
			</div>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"onclick="saves()">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
	

</body>


</html>