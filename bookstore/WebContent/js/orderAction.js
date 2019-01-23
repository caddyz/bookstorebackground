// 全局变量
var url = "";					// url路径
var $_index = "";			// 点击行的索引
var $row = "";				// 点击行的值
var $form = {};				// 修改时用户输入的值
var $address = "";		// 地址信息

$(function() {
	
	// 单击事件
	$("#dg").datagrid({
		onSelect : function(index, row) {
			$_index = index;
			$row = row;
		}
	});

	// 双击事件
	$("#dg").datagrid({
		onDblClickRow : function(index, row) {
			$('#winInfo').window('open');

			$.ajax({
				url : '/bookstore/orderController/getOrderWinInfo',
				dataType : "json",
				async : false,
				data : {
					orderId : row.orderId,
				},
				success : function(data) {
					$("#orderInfo").datagrid('loadData', data);
				}
			});
		}
	});
});

// 修改订单
function editOrder() {
	if ("已完成" != $row.orderStatus && "已评价" != $row.orderStatus) {
		$("input[name=orderId]").attr("readonly", "readonly");
		
		if ($row.orderStatus == "待发货") {
			$("#os1").attr("checked", true);
		} else {
			$("#os2").attr("checked", true);
		}
		
		var as = $row.address.split(", ");
		if ($row) {
			$("#province").append("<option selected='selected'>" + as[0] + "</option>");
			$("#citys").append("<option selected='selected'>" + as[1] + "</option>");
			$("#county").append("<option selected='selected'>" + as[2] + "</option>");
			$("#detail").val(as[3]);
			$('#dlg').dialog('open').dialog('setTitle', '修改订单信息。注意：订单号不可修改！！');
			$('#fm').form('load', $row);
			url = 'updateOrder';
		} 
	} else {
		$.messager.alert("提示", "已完成的订单不能修改！");
	}
}

// 改变订单状态
function updateStatus(rows){
	$.each(rows, function(i, item){
		switch (item.orderStatus) {
			case "1" :
				item.orderStatus = "待发货";
				break;
			case "2" :
				item.orderStatus = "已发货";
				break;
			case "3" :
				item.orderStatus = "已取消";
				break;
			case "4" :
				item.orderStatus = "退货中";
				break;
			case "5" :
				item.orderStatus = "已完成";
				break;
			case "6" :
				item.orderStatus = "已评价";
				break;
			case "7" :
				item.orderStatus = "退货中（已确认）";
				break;
			default :
				item.orderStatus = "待付款";
				break;
		}
	})
}

// 退货提示
function salesMessage(){
	if ($row.orderStatus == "退货中") {
		salesReturn();
	}  else if ($row.orderStatus == "退货中（已确认）") {
    	$.messager.alert('提示','该订单您已确认退货...'); 
    } else {
    	$.messager.alert('警告','该订单未申请退货！'); 
	}
}

// 退货
function salesReturn() {
	$.messager.confirm('确认','您确认同意退货吗？', function(flag){
	    if (flag && $row.orderStatus == "退货中") {
	        $.ajax({
	        	url : '/bookstore/orderController/updataStatus',
	        	dataType : 'json',
	        	async : false,
	        	data : {
	        		orderStatus : $row.orderStatus, 
	        	},
	        	success : function(data){
	        		if (data.state) {
	        			$('#dg').datagrid('updateRow', {
	    					index : $_index,
	    					row : {
	    						orderStatus : "退货中（已确认）",
	    					}
	    				});
	        		}
	        		
	        		$.messager.show({
	        			title : '提示信息',
	        			msg : data.msg,
	        			timeout : 5000,
	        			showtype : 'fade'
	        		})
	        		
	        	},
	        	error : function(err){
	        		console.log(err);
	        		$.messager.alert("内部错误");
	        	}
	        });
	    }
	}); 
}

// 提交事件，用户点击保存/确认时触发
function saveOrder() {
	var status = $("input:radio:checked").val();

	// easyui对ajax的封装
	$('#fm').form(
			'submit', {
				url : url,
				onSubmit : function() {
					var temp = $('#fm').serializeArray();
					$.each(temp, function(i, item) {
						$form[item.name] = item.value;
					});
					// 进行表单字段验证，当全部字段都有效时返回 true 。该方法和 validatebox 插件一起使用。
					return $('#fm').form('validate');// jquery $("#") $('#fm').form('validate') 会去调用验证框架进行验证，然后对所有easyui-validateBox验证结果进行与操作，全部ok表单才可以提交
				},
				success : function(result) {
					var result = eval('(' + result + ')');// 数据解析，如果是json数据就解析成json

					// 如果返回信息为true，代表数据库修改成功
					if (result.state) {

						// 用户修改信息时有可能不会修改地址(省/市/区)
						// 这里判断如果没修改就把原先的地址赋值给$address变量
						var pcc = $form.province + ', ' + $form.citys + ', ' + $form.county;
						if ('省份, 城市, 县/区' != pcc) { // 修改了省/市/区

							// 判断详细地址，如果用户没写详细地址(某小区/某门市)，修改时就默认给个“自取”
							if ("" == $form.detail) {
								$address = pcc + ", 自取";
							} else {
								$address = pcc + ', ' + $form.detail;
							}

						} else { // 如果没修改省/市/区
							$address = $row.address;
						}
						var orderStatus1 = "";
						if ($form.orderStatus1 == 1) {
							orderStatus1 = "待发货";
						} else {
							orderStatus1 = "已发货";
						}
						// 更新页面显示的信息 不用手动刷新
						$('#dg').datagrid('updateRow', {
							index : $_index,
							row : {
								expressName 			: $form.expressName,
								addressConsignee 	: $form.addressConsignee,
								mobile 						: $form.mobile,
								address 						: $address,
								totalPrice 					: $form.totalPrice,
								orderStatus 				: orderStatus1,
							}
						});
						$('#dlg').dialog('close'); // 关闭窗口
					}

					$.messager.show({
						title : '提示信息！',
						msg : result.msg
					});
				}
			});
}

// 删除订单的方法
function removeOrder() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$.messager.confirm('确认删除','是否确认删除该订单？', 
			function(cfm) {
				if (cfm) {
					// $.post(url,data,success(data, textStatus,xhr),dataType)
					$.post('removeOrder', {orderId : row.orderId},
					function(result) {
						if (result.state) {
							$.messager.show({   // show success message
								title : 'Success',
								msg : result.msg
							});
							$("#dg").datagrid('deleteRow', $_index);
						} else {
							$.messager.show({   // show failure message
								title : 'Failure',
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
