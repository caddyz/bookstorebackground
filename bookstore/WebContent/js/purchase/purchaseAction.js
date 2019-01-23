var url = "";// 定义一个全局变量传给id为fm的form提交事件


let $purchaseId  = '';
let $purchaseBatch  = '';
let $purchaseDate  = '';
let $purchaseStatus  = '';
let $purchaseLog  = '';
let $currentPage  = '';
let $pageSize  = '';
let $supplier  = '';
let $startDate  = '';
let $endDate  = '';
let $employee='';
let $admin='';
let $UniformResourceLocation='http://'+window.location.host+'/bookstore/';


	function doSearch(){ 
	
		$.ajax({			
			url:$UniformResourceLocation+'purchase/myPurchaseGet',
			dataType:'json',
			async:false,
			data:{
				
				purchaseId: $purchaseId,
				purchaseBatch		:$purchaseBatch,
				supplier		:$supplier,
				employee		:$employee,	
				startDate		:$startDate,
				endDate	:$endDate,
				purchaseStatus			:$purchaseStatus,
				currentPage			:1,
				pageSize			:10,
			},
			success:function(data){
				transferStatus(data.value.rows);
				console.log(data);
				transferEmp(data.value.rows);
				transferSupplier(data.value.rows) ;
				console.log(data);
				$("#dg").datagrid('loadData',data.value);				
			}
		});
		
   
	}
	
	$(function(){	
		$("#dg").datagrid({  
			   onDblClickRow: function (index, row){
					 purchaseProFun(row);	
			   } ,
		
		onClickRow : function(index, row) {
			$("#tips").show();
			 $("#tips").html("双击查看修改或查看采购详情");
			 $("#tips").css("color","rgba(255,0,0,0.9)");
			 $('#tips').delay(2000).hide(0);
		}
		
		
		
		            });
		
		$('#dg').datagrid({    
	 		 columns:[[    
	 		 {field:'purchaseId',hidden:true},    
	 		 {field:'purchaseBatch',title:'采购批次',width:100},    
	 		 {field:'purchaseDate',title:'采购日期',width:100,align:'center'}   ,
	 		 {field:'supplier',title:'供应商',width:100,align:'center'}    ,
	 		 {field:'employee',title:'员工',width:100,align:'center',hidden:true}    ,
	 		 {field:'purchaseStatus',title:'采购状态',width:100,align:'center' }  ,
	 		 
	 		 {field:'supplierId',title:'采购id',hidden:true},
	 		 {field:'purchaseLog',title:'备注',hidden:false}
	 		 ]] ,
	 		 pagination:true,
	 		 pageList:[5,10,20],
	 		 fitColumns:true,
	 		 striped:true,
	 		 rownumbers:true,
	 		 singleSelect:true
	 		 });   
		
		doSearch();	
		
		// 双击查看详情
	
		
	
		var pager = $('#dg').datagrid('getPager');	
		pager.pagination({
			pageSize:10,
			pageNumber:1,
			showPageList:true,
			onSelectPage:function(pageNumber, pageSize){
				$.ajax({
					url:$UniformResourceLocation+'purchase/myPurchaseGet',
					dataType:'json',
					async:false,
					data:{						
						purchaseId: $purchaseId,
						purchaseBatch		:$purchaseBatch,
						supplier		:$supplier,
						employee		:$employee,
						startDate		:$startDate,
						endDate	:$endDate,
						purchaseStatus			:$purchaseStatus,
						currentPage			:pageNumber,
						pageSize			:pageSize,
					}, 
					success:function(data){
						transferStatus(data.value.rows);
						transferEmp(data.value.rows);
						transferSupplier(data.value.rows) ;
						$("#dg").datagrid('loadData',data.value);
						console.log(data);
					}
				});		
			}			
		});
	});	
	

	
	$(function(){
		

		$("#PurchaseInfo").dialog({
			onClose: function () {
				$(".status2").show();
				$(".status3").show();
				$(".status1").show();
			}
		});


		
		
//		设置查询时间格式
		$('#startDate').datebox({
		    onSelect: function(date){
		        $startDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    }
		});	
		$('#endDate').datebox({
		    onSelect: function(date){
		        $endDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    }
		});	
//查询选框中数据
		$('#searchBatchC').combobox({
			url:$UniformResourceLocation+'purchase/getBatch',
			valueField : 'id',
			textField : 'text',
			onSelect : function(record) {
				$purchaseBatch = record.id;
			}
		});
//查询fm 中可用的供应商
		$('#postSupplier').combobox({
			url:$UniformResourceLocation+'supplier/getSupplierOnLine',
			valueField : 'id',
			textField : 'text',
			onSelect : function(record) {
				$supplier = record.id;
				$('#postSupplier').val()=record.id;
			}
		
		});
			
		$('#addStatus').combobox({
			onSelect: function(record){
				$purchaseStatus = record.text;
			}
		});	

	})
	
	




	
	function transferStatus(rows) {
		$.each(rows, function(i, item) {
			switch (item["purchaseStatus"]) {
			case 0:
				item["purchaseStatus"] = "已取消";
				break;
			case 1:
				item["purchaseStatus"] = "计划中";
				break;
			case 2:
				item["purchaseStatus"] = "进行中";
				break;
			case 3:
				item["purchaseStatus"] = "待入库";
				break;
			case 4:
				item["purchaseStatus"] = "已完成";
				break;
			default:
				break;
			}
		})
	}

	function transferEmp(rows) {
		$.each(rows, function(i, item) {
			item["employee"] = item["employee"].empName;
		})
	}
	
	function transferSupplier(rows) {
		$.each(rows, function(i, item) {
			item["supplierId"] = item["supplier"].supplierId;
			item["supplier"] = item["supplier"].supplierName;
		})
	}










function fmReset(){
	
	var row=$('#dg').datagrid('getSelected');
	$('#fm').form('load', row);
}
function fmClear(){
	$('#fm').form('clear');
}
function dgClose(){
$('#postSupplier').dialog('close');

}





function RandomBatch(){
	
	        var date = new Date();
	        var seperator1 = "-";
	        var year = date.getFullYear();
	        var month = date.getMonth() + 1;
	        var strDate = date.getDate();
	        if (month >= 1 && month <= 9) {
	            month = "0" + month;
	        }
	        if (strDate >= 0 && strDate <= 9) {
	            strDate = "0" + strDate;
	        }
	        var currentdate = year + seperator1 + month + "Batch";
	        return currentdate;
	        
}

//新增订单

function postPurchase() {
	$("#purchaseMan").hide();
	$("#purchaseStatusFm").hide();
	$('#PurchaseInfo').dialog('open').dialog('setTitle','创建新的采购');

	$('#dlg-buttons').html(
			"<button  onclick='savePurchase()'>提交</button> "
					+ "<button onclick= 'fmClear()'>清空</button>"

	)
	
	url = '/bookstore/purchase/postPurchase';
	$('#fm').form('clear');
	$('#postBatch').textbox('setValue',RandomBatch());


}


	
	
	


//修改订单


function putPurchase() {
	var row = $('#dg').datagrid('getSelected');
	console.log(row);
	$('#dlg-buttons').html(
			
			"<button  onclick='savePurchase()'>提交</button> "
					+ "<button onclick= 'fmReset()'>重置</button>"

	)
	if(row){
		
	if(row.purchaseStatus=="待入库"||row.purchaseStatus=="已完成"){
		
		$.messager.alert('提示',row.purchaseStatus+'项目无法修改');
		
	}else {
		if(row.purchaseStatus=="计划中"||row.purchaseStatus=="已取消"){
			$(".status2").hide();
			$(".status3").hide();
		}else 
		if(row.purchaseStatus=="进行中"){
			$(".status1").hide();
			$(".status0").hide();
			$(".status3").hide();
		}
		
		$('#PurchaseInfo').dialog('open').dialog('setTitle', '修改订单信息');
		
		$('#fm').form('load', row);
		
		
		console.log(row);
		url = '/bookstore/purchase/putPurchase';
	}
	
	}else{
		$.messager.alert('提示','请选择项目');
	}

	
}


//保存修改或者新增
function savePurchase() {
	// easyui对ajax的封装
	console.log(url);
	$('#fm').form('submit', {
		url : url,
		onSubmit : function() {
			// 进行表单字段验证，当全部字段都有效时返回 true 。该方法和 validatebox 插件一起使用。
			return $('#fm').form('validate');// jquery $("#")
												// $('#fm').form('validate')
												// 会去调用验证框架进行验证，然后对所有easyui-validateBox验证结果进行与操作，全部ok表单才可以提交
		},
		success : function(result) {
			var result = eval('(' + result + ')');// 数据解析，如果是json数据就解析成json
			if (result) {
				$('#PurchaseInfo').dialog('close'); // close the dialog
				doSearch();
				if(result.value==0){
					$.messager.alert({
						title : '提示消息',
						msg : "操作失败！请检查！"
					});
				}else{
					$.messager.show({
						title : '提示消息',
						msg : result.msg
					});
				}
			
				
			} else {
				$.messager.show({
					title : '提示消息',
					msg : result.msg
				});
			}
		}
	});
}




function delPurchase() {
	var row = $('#dg').datagrid('getSelected');
	console.log(row);

	if (row) {
		if(row.purchaseStatus=="待入库"||row.purchaseStatus=="已完成"){
			
			$.messager.alert({ // show error message
				title : '警告消息',
				msg : row.purchaseStatus+"无法删除",
				timeout:1000,
				showType:'slide'
				
			});
			
			
		}else{
			
			
			$.messager.confirm('Confirm', '确定要删除该采购信息?', function(cfm) {
				if (cfm) {
				
					$.post('/bookstore/purchase/delPurchase', {
						purchaseId : row.purchaseId,
					},

					function(result) {
						if (result) {
							$('#dg').datagrid('reload'); // reload the user data
							$.messager.show({ // show error message
								title : '提示消息',
								msg : result.msg
								
							});
							doSearch();
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



}