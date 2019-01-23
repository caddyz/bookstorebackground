var url = "";// 定义一个全局变量传给id为fm的form提交事件
var $_index;
var $row;
var $form={};  // 定义空对象

$(function() {
	$("#dg").datagrid({
		// 点击某一行时获取到该行的索引和该行所有的参数
		onSelect : function(index, row) {
			$_index = index;
			$row = row;
		}
	});
});

function addPrint() {
	$('#dlg').dialog('open').dialog('setTitle', '请正确的输入印刷厂的相关信息');
	$('#fm').form('clear');
	url = '/bookstore/printController/addPrint';
}

function editPrint() {
	$("input[name=printId]").attr("readonly", "readonly")
	// datagrid的getSelected方法是返回数据网格的第一个选中的行或者 null。
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('setTitle',
				'修改印刷厂的信息！注意：印刷商的编号不能修改...');
		$('#fm').form('load', row);
		url = '/bookstore/printController/updatePrint';
	}
}
// id为fm的form的提交事件，由用户信息编辑对话框的确定按钮点击触发
function savePrint() {
	// easyui对ajax的封装
	$('#fm').form('submit', {
		url : url,
		onSubmit : function() {
			// 获取输入的数据    name  value
			var temp = $('#fm').serializeArray();
			$.each(temp, function(i, item){
				// 循环给空对象赋值
				$form[item.name] = item.value;
			})
			
			// 进行表单字段验证，当全部字段都有效时返回 true 。该方法和 validatebox 插件一起使用。
			return $('#fm').form('validate');// jquery $("#")  $('#fm').form('validate') 会去调用验证框架进行验证，然后对所有easyui-validateBox验证结果进行与操作，全部ok表单才可以提交
		},
		success : function(result) {
			var result = eval('(' + result + ')');// 数据解析，如果是json数据就解析成json
			if (result.state) {
				if (url == 'addPrint'){
					$('#dg').datagrid('appendRow', {
						printerName : 	$form.printerName,	
						printId : 			$form.printId,
						printSize : 			$form.printSize,
						printDate : 		$form.printDate,
						printQuantity:	$form.printQuantity,
						printBatch : 		$form.printBatch
					});
				} else {
					$('#dg').datagrid('updateRow', {
						index : $_index,
						row : $form
					});
				}

				$('#dlg').dialog('close'); // close the dialog
			}
			$.messager.show({
				title : '提示信息！',
				msg : result.msg
			});
		}
	});
}

// 删除印刷厂的方法
function removePrint() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$.messager.confirm('Confirm',
				'Are you sure you want to remove this printer?', function(cfm) {
					if (cfm) {
						// $.post(url,data,success(data,
						// textStatus,xhr),dataType)
						$.post('/bookstore/printController/removePrint', {
							printId : row.printId
						}, function(result) {
							if (result.state) {
								$.messager.show({ // show success message
									title : 'Success',
									msg : result.msg
								});
								$('#dg').datagrid('deleteRow', $_index)
							} else {
								$.messager.show({ // show error message
									title : 'Error',
									msg : result.msg
								});
							}
						}, 'json'// 返回的数据类型
						);
					}
				});
	}
}
