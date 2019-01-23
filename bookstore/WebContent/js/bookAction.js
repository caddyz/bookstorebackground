// 全局变量
var url = "";
var $_index = "";
var $row = "";
var $form = {};

$(function(){
	// onSelect：点击事件
	$("#dg").datagrid({onSelect:function(index, row){
			$_index = index;
			$row = row;
		}
	});
	
	// onDblClickRow：双击事件
	$("#dg").datagrid({
			onDblClickRow : function(index, row){
			$('#bookAll').window('open');  
			
			$.ajax({
				url:'/bookstore/printController/getPrinterByPrintId', 
				dataType : "json",
				async : false,
				data : {
					printId	: 	row.printId,
				}, 
				success: function(data){
					$("#printTB").datagrid('loadData', data.value);
				}
			});
		}
	});
});


// 查看书的评论
function getBookMsg(){
	$("#msg").window("open"); // 打开窗口
	$.ajax({
		url:'/bookstore/bookController/getMessage', 
		dataType : "json",
		async : false,
		data : {
			bookId	: 	$row.bookId,
		}, 
		success: function(data){
			if (0 < data.length) {
				$("#msgTB").datagrid('loadData', data);
			} else {
				$.messager.alert("提示信息", "该书没有评论！");
				$("#msg").window("close");
			}
		}
	});
}

// 添加书
function addBook() {
	$('#dlg').dialog('open').dialog('setTitle', '请正确的输入书的相关信息');
	$('#fm').form('clear');
	url = 'addBook';
}

// 修改书
function editBook() {
	// 隐藏作者
	$("#aId").css('display', 'none');
	$("#aName").css('display', 'none');
	// datagrid的getSelected方法是返回数据网格的第一个选中的行或者 null。
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog('open').dialog('setTitle', '修改书的信息');
		$('#fm').form('load', row);
		url = 'updateBook';
	}
}


// 保存按键，用户点击保存时触发
function saveBook() {
	var bookStatus = $("#bookStatus1").combobox("getValue");
	if (bookStatus == "在售" && $row.stockNum <= 0) {
		$.messager.alert("提示", "该书库存不足一本，不能上架！");
	} else {
		// easyui对ajax的封装
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
		            var temp = $('#fm').serializeArray();
		            $.each(temp, function (i, item) {
		            	$form[item.name] = item.value;
		            });
				// 进行表单字段验证，当全部字段都有效时返回 true 。该方法和 validatebox 插件一起使用。
				return $('#fm').form('validate');// jquery $("#")  $('#fm').form('validate') 会去调用验证框架进行验证，然后对所有easyui-validateBox验证结果进行与操作，全部ok表单才可以提交
			},
			success : function(result) {
				var result = eval('(' + result + ')');// 数据解析，如果是json数据就解析成json
				if (result.state) {
					if (url == 'addBook') {
						$('#dg').datagrid('appendRow', {   // 添加时自动刷新
							bookId 					: $form.bookId,
							bookName 			: $form.bookName,
							authors 					: $form.authorName,
							bookCategory 		: $form.bookCategory,
							bookSalesPrice 	: $form.bookSalesPrice,
							bookProfile 			: $form.bookProfile,
							publishName 		: $form.publishName,
							bookCoverImage 	: $form.bookCoverImage,
							bookStatus 			: $form.bookStatus,
							printId 					: $form.printId,
							stockNum 				: 0,
							stockSales				: 0
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
	
}


// 删除书
function removeBook() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$.messager.confirm('Confirm','你确定要删除这本书吗？', 
			function(cfm) {
				if (cfm) {
					// $.post(url,data,success(data, textStatus,xhr),dataType)
					$.post('removeBook', {bookId : row.bookId},
					function(result) {
						if (result.state) {
							$.messager.show({ // show success message
								title : 'Success',
								msg : result.msg
							});
							$("#dg").datagrid('deleteRow', $_index);
							// $('#dg').datagrid('reload'); // 重新加载数据
						} else {
							$.messager.show({ // show error message
								title : 'Error',
								msg : result.msg
							});
						}
					}, 'json'// 返回的数据类型
					);
				}
			}
		);
	}
}
