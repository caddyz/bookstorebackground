var url = "";
var $_index;
var $row;
var $form = {};
var $result;
function clearBox(){
	// 如果有权限复选框，将复选框清空
	if(document.getElementById("checkAll")){
		$('#permissionBox').attr("checked",false);
		$('.permissionBox').prop("checked", false);
	}
}
$(function(){
//	$('body').hide();
//	$('body').fadeIn("slow");
	$("#dg").datagrid({
		onSelect:function(index, row){
			$_index = index;
			$row	= row;
		}
	})
})

// 每次查询返回到第一页
function setFirstPage(ids) {
		var opts = $(ids).datagrid('options');
		var pager = $(ids).datagrid('getPager');
		opts.pageNumber = 1;
		opts.pageSize = opts.pageSize;
		pager.pagination('refresh', {
			pageNumber : 1,
			pageSize : opts.pageSize
		});
	}


function news(src) {
	$('#dlg').dialog('open').dialog('setTitle', '');
	clearBox();
	$('#fm').form('clear');
	url = src;
}
function edits(src) {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('setTitle', 'Edit User');
		$('#fm').form('load', row);
		console.log(row)
		clearBox();
		if(document.getElementById('adminEmpId')){
			$('#adminEmpId').combogrid('setValue', {empId:$row.emp.empId,empName:$row.adminEmpName});
			// 复选框拉取管理员的权限
			$.ajax({
				url:'/bookstore/loginCtrl/getAdminPermissions',
				data:{
					adminAccount:row.adminAccount
				},
				success:function(data){
					$.each(data,function(i,item){
						$(":checkbox[value='"+item+"']").prop("checked", true);
					})
				}
				
			})
			
		}
		if(document.getElementById("#empName1")){
			$("#empName1").textbox('setValue', $row.empName);
		}
		if(document.getElementById('ccjob')){
			// 如果有职位下拉菜单，则查询职位信息
			$.ajax({
				url:'/bookstore/employee/getJob',
				data:{
					empId:row.empId
				},
				success:function(data){
					$('#ccjob').combobox('setValue', data["jobName"]);
				}
			})
		}
		url = src;
	}
}

function saves() {
	// 如果是管理账户的增加或修改，要规定至少选一个权限复选框
	if(document.getElementsByClassName("permissionBox").length > 0){
		if($("input:checkbox[name='permission']:checked").length <= 0){
			$.messager.alert('失败','至少选择一个权限')
			return false;
		}
	}
	
	$('#fm').form('submit', {
		url : url,
		onSubmit : function() {
			var temp = $('#fm').serializeArray();
			$.each(temp, function(i,item){
				$form[item.name] = item.value;
				if(document.getElementById("adminEmpId")){
					$form.adminEmpName = $adminEmpName;
				}
			})
			return $('#fm').form('validate');// jquery $("#")
			// $('#fm').form('validate')
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			$result = result;
			if (result.state) {
				// 如果是添加，则调用表格的appendRow
				if(url.indexOf("add") != -1){
					if(result.value != 1){
						// 如果controller返回的是对象，则处理$result中的数据，将数据插入表格
						$("#change").click();
						$('#dg').datagrid('appendRow',$result.value);
					}else{
						// 如果controller返回的是1，则从提交的表单中获取数据插入表格
						$('#dg').datagrid('appendRow',$form);
					}
					
				}else{
					$('#dg').datagrid('updateRow',{
						index : $_index,
						row	  : $form
					})
				}
				$('#dlg').dialog('close'); // close the dialog
				$.messager.show({
					title : 'success',
					msg : result.msg
				});
			} else {
				$.messager.show({
					title : 'error',
					msg : result.msg
				});
			}
		}
	});
}

function removes(src) {
	$.ajax({ 
		// 获取admin对象中的jobLevel，判断职位级别。
		url : '/bookstore/loginCtrl/getAdmin/',
		success : function(data) {
			if (data.emp.job.jobLevel <= 2) {
				var row = $('#dg').datagrid('getSelected');
				if (row) {
					$.messager.confirm('Confirm', '确定从数据库中彻底删除吗',
							function(cfm) {
								if (cfm) {
										data = {primaryId : row.primaryId};
									// $.post(url,data,success(data,
									// textStatus,xhr),dataType)
									$.post(src, data, function(result) {
										if (result.state) {
											$.messager.show({ // show error
												title : 'success',
												msg : result.msg
											});
											$("#dg").datagrid('deleteRow', $_index)
										} else {
											$.messager.show({ // show error
												title : 'Error',
												msg : result.msg
											});
										}
									}, 'json');
								}
							});
				}
			}else{
				$.messager.show({ // show error
					title : 'Error',
					msg : '只有经理和总经理可以执行删除'
				});
			}

		},
		error : function(error) {
			console.log(error)
		}
	})
	

}