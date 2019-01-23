var url = "";// 定义一个全局变量传给id为fm的form提交事件
/* var data = {}; */

let $supplierName = '';
let $startDate = '';
let $endDate = '';
let $cooperateStatus = '';
let $currentPage = 0;
let $pageSize = 10;
let $UniformResourceLocation='http://'+window.location.host+'/bookstore/';

function doSearch() {
	$.ajax({
		url : $UniformResourceLocation + 'supplier/getSupplierByCondition',
		dataType : 'json',
		async : false,
		data : {
			supplierName : $("#searchName").val(),
			startDate : $startDate,
			endDate : $endDate,
			currentPage : $currentPage,
			cooperateStatus : $cooperateStatus,
			pageSize : $pageSize
		},
		success : function(data) {
			console.log(data);
			$("#dg").datagrid('loadData', data.value);
		},
		error : function(err) {
			console.log(err)
		}
	});
}

// 分页方法
$(function() {

	$("#dg").datagrid({
		onDblClickRow : function(index, row) {
			putSupplier();
		},
	onClickRow : function(index, row) {
		$("#tips").show();
		 $("#tips").html("双击修改信息");
		 $("#tips").css("color","rgba(255,0,0,0.9)");
		 $('#tips').delay(2000).hide(0);
	}
	
	
	});

	$('#dg').datagrid({
		columns : [ [ {
			field : 'supplierId',
			title : 'id',
			hidden : true
		}, {
			field : 'supplierName',
			title : '供应商',
			width : 100,
			align : 'center'
		}, {
			field : 'supplierPhone',
			title : '联系电话',
			width : 100,
			align : 'center'
		}, {
			field : 'supplierAddress',
			title : '地址',
			width : 100,
			align : 'center'
		}, {
			field : 'supplierContact',
			title : '联系人',
			width : 100,
			align : 'center'
		}, {
			field : 'supplierContactPhone',
			title : '个人电话',
			width : 100,
			align : 'center'
		}, {
			field : 'cooperateDate',
			title : '合作日期',
			width : 80,
			align : 'center'
		}, {
			field : 'cooperateStatus',
			title : '合作状态',
			width : 50,
			align : 'center',
			formatter: function(value,row,index){
				if (row.cooperateStatus==0){
					return '未合作';
				} else if(row.cooperateStatus==1){
					return '合作中';
				}else if(row.cooperateStatus==2){
					return '合作过';
				}
			}
		}
		] ],
		pagination : true,
		pageList : [ 5, 10, 20 ],
		fitColumns : true,
		striped : true,
		rownumbers : true,
		singleSelect:true
	});

	doSearch();
	var pager = $('#dg').datagrid('getPager');
	pager.pagination({
		pageSize : 10,
		pageNumber : 1,
		showPageList : true,
		onSelectPage : function(pageNumber, pageSize) {
			console.log($cooperateStatus);
			$.ajax({
				url : $UniformResourceLocation
						+ 'supplier/getSupplierByCondition',
				dataType : 'json',
				async : false,
				data : {
					supplierName : $("#searchName").val(),
					startDate : $startDate,
					endDate : $endDate,
					currentPage : pageNumber,
					cooperateStatus : $cooperateStatus,
					pageSize : pageSize
				},
				success : function(data) {
					$("#dg").datagrid('loadData', data.value);

				}
			});
		}
	});
});

$(function() {

	$('#startDate').datebox(
			{
				onSelect : function(date) {
					$startDate = date.getFullYear() + "-"
							+ (date.getMonth() + 1) + "-" + date.getDate();
				}
			});
	$('#endDate').datebox(
			{
				onSelect : function(date) {
					$endDate = date.getFullYear() + "-" + (date.getMonth() + 1)
							+ "-" + date.getDate();
				}
			});
	$('#searchStatus').combobox({
		onSelect : function(record) {
		$cooperateStatus = record.text;
		}
	});

	
	//$cooperateStatus = $(".cooperateStatus").val()
	console.log($cooperateStatus);

})


//添加供应商。设置按钮 和清空表单
function postSupplier() {
	$('#postSupplier').dialog('open').dialog('setTitle', '添加供应商');

	$('#dlg-buttons').html(
			"<button  onclick='saveSupplier()'>提交</button> "
					+ "<button onclick= 'fmClear()'>清空</button>"

	)

	url = '/bookstore/supplier/postSupplier';
	$('#fm').form('clear');
}

function putSupplier() {
	var row = $('#dg').datagrid('getSelected');

	$('#dlg-buttons').html(
			"<button  onclick='saveSupplier()'>提交</button> "
					+ "<button onclick= 'fmReset()'>恢复</button>"

	)

	if (row) {
		$('#postSupplier').dialog('open').dialog('setTitle', '修改供应商信息');
		$('#fm').form('load', row);
		console.log(row.supplierId)
		console.log(row)
		url = '/bookstore/supplier/putSupplier';
	}
}

function fmReset() {

	var row = $('#dg').datagrid('getSelected');
	$('#fm').form('load', row);
}
function fmClear() {
	$('#fm').form('clear');
}
function dgClose() {
	$('#postSupplier').dialog('close');

}

function supplierPro(row) {
	
var cooperateStatusText='';

	if (row) {
		
		switch (row.cooperateStatus) {
		case 0:
		cooperateStatusText="未合作";
		break;
		case 1:
		cooperateStatusText="合作中";
		break;
		case 2:
		 cooperateStatusText="合作过";
		 break;
		default:
		cooperateStatusText= "未知";
		break;
		}
		$('#supplierPro').dialog('open').dialog('setTitle', '详细信息');
		$("#titlePro").html(row.supplierName);
		$("#idPro").html(row.supplierId);
		$("#phonePro").html(row.supplierPhone);
		$("#datePro").html(row.cooperateDate);
		$("#statusPro").html(cooperateStatusText);
		$("#contactPro").html(row.supplierContact);
		$("#cPhonePro").html(row.supplierContactPhone);

	}

}

function saveSupplier() {


	$('#fm').form('submit', {
		url : url,
		onSubmit : function() {
			return $('#fm').form('validate');// jquery $("#")
		},
		success : function(result) {
			var result = eval('(' + result + ')');// 数据解析，如果是json数据就解析成json
			if (result) {

				$('#dlg').dialog('close'); // close the dialog
				$('#dg').datagrid('reload'); // reload the user data
				$.messager.show({
					title : '提示消息',
					msg : result.msg
				});
			} else {
				$.messager.show({
					title : '提示消息',
					msg : result.msg
				});
			}
			$('#postSupplier').dialog('close');//关闭窗口
			doSearch();//重新搜索数据
		}
	});
}

function delSupplier() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$.messager.confirm('Confirm', '确定要删除供应商?', function(cfm) {
			if (cfm) {
				// $.post(url,data,success(data, textStatus,xhr),dataType)
				$.post('/bookstore/supplier/delSupplier', {
					supplierId : row.supplierId,
				},

				function(result) {
					if (result) {
						$('#dg').datagrid('reload'); // reload the user data
						$.messager.show({ // show error message
							title : '提示消息',
							msg : result.msg
						});
						doSearch();//重新搜索数据
					} else {
						$.messager.show({ // show error message
							title : '提示消息',
							msg : result.msg
						});
					}
				}, 'json'// 返回的数据类型
				);
			}
		});
	}
}
