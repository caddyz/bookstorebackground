var url = "";// 定义一个全局变量传给id为fm的form提交事件


let $currentPagePro  = '';
let $pageSizePro  = '';
var $rowPro='';
var $totalPay='';

function doProSearch(rowPro){ 
	$currentPagePro=1;
	$pageSizePro=10;
		$.ajax({			
			url:$UniformResourceLocation+'purchaseItem/getByPurchaseId',
			dataType:'json',
			async:false,
			data:{
				purchaseId:  rowPro,
				currentPage	:$currentPagePro,
				pageSize:$pageSizePro,
			},
			success:function(data){
				console.log(data);
				if (data.key !== null && data.key !== undefined && data.key !== '') { 
					$totalPay=data.key;
					} else{
						$totalPay="0";
					}
				$("#dgPro").datagrid('loadData',data.value);				
				console.log(data);
				console.log($totalPay);
			
			}
		});
	
	}


//窗口中加载row 数据 并且初始化第一页 数据
function purchaseProFun(row) {
	//若为已入库和 则隐藏工具栏
	if(row.purchaseStatus=="待入库"||row.purchaseStatus=="已完成"){
		$("#toolBarPro").hide();
		}else{
			$("#toolBarPro").show();
		}
	if(row){
		
		$('#purchasePro').dialog('open').dialog('setTitle','采购详单');
	     $("#titlePro").html(row.purchaseId +'号 采购单明细');
	     $("#idPro").html(row.purchaseId);
	     $("#batchPro").html(row.purchaseBatch);
	     $("#datePro").html(row.purchaseDate);
	     $("#empPro").html(row.employee);
	     $("#suppliePro").html(row.supplier);
	     $("#statusPro").html(row.purchaseStatus);
	     $("#logPro").html(row.purchaseLog);
	     rowPro=row.purchaseId;
	     
	}
	
	 doProSearch(rowPro);
	
     $("#totalPay").html($totalPay);
	
}

$(function(){
	
	$('#dgPro').datagrid({ 
		 columns:[[    
		 {field:'purchaseItemId',hidden:true},    
		 {field:'purchaseId'},    
		 {field:'book',title:'采购内容',width:100,
			 
			 formatter: function(value,row,index){
					if (row.book){
						return row.book.bookName;
					} else {
						return '未知';
					}
				}
			 
		 
		 
		 },    
		 
		 {field:'purchaseNum',title:'采购数量',width:45,align:'center'}   ,
		 {field:'purchasePrice',title:'采购价￥',width:45,align:'center'}    ,
		 {field:'totalPrice',title:'合计￥',width:45,align:'right',
			 
			 formatter: function(value,row,index){
				 if(row.purchasePrice){
					 return row.purchasePrice*row.purchaseNum;
				 }
				}

		 
		 }   
		 ]] ,
		 pagination:true,
 		 pageList:[20,40,60],
 		 fitColumns:true,
 		 striped:true,
 		 toolbar:$("#toolBarPro"),
// 		 rownumbers:true
		 });
	
	var pagerPro = $('#dgPro').datagrid('getPager');	
	pagerPro.pagination({
		pageSize:20,
		pageNumber:1,
		showPageList:true,
		onSelectPage:function(pageNumber, pageSize){
			$.ajax({
				url:$UniformResourceLocation+'purchaseItem/getByPurchaseId',
				dataType:'json',
				async:false,
				data:{						
					purchaseId:  rowPro,
					currentPage			:pageNumber,
					pageSize			:pageSize,
				}, 
				success:function(data){

					$("#dgPro").datagrid('loadData',data.value);
					console.log(data);
				}
			});		
		}	
	});
	
})




	
	$(function(){
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
		
		$('#searchBatchC').combobox({
			url:$UniformResourceLocation+'purchase/getBatch',
			valueField : 'id',
			textField : 'text',
			onSelect : function(record) {
				$purchaseBatch = record.text;
			}
		});
	
		
		
		$('#searchStatus').combobox({
			onSelect: function(record){
				$purchaseStatus = record.text;
			}
		});		
		$('#addStatus').combobox({
			onSelect: function(record){
				$purchaseStatus = record.text;
			}
		});	
		//查询书名字
		$('.ccbookName').combobox({
			url:$UniformResourceLocation+'activity/allBookName',    
		    valueField:'text',    
		    textField:'text',
			onSelect: function(record){
				$bookName = record.text;
			}
		});		

	
	})
	
	


	




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













function openPII() {
	$('#PIInfo').dialog('open').dialog('setTitle','');

	$('#dataPII').datagrid({     
	    columns:[[    
			 {field:'purchaseId',title:'采购id',width:100},    
			 {field:'bookId',title:'采购内容',width:100},    
			 {field:'purchaseNum',title:'采购数量',width:50,align:'center'}  ,
			 {field:'purchasePrice',title:'采购价',width:50,align:'center'}
	    ]],
	    rownumbers:true
	   
	});  

}

// 判断输入内容是否合法 合法则添加 ，否则不添加
function appendPII() {
	//tipsPII 为 添加项目窗口的提示标签
	
	 $('#tipsPII').show();
	var price= $("#pricePII").val();
	var number = $("#numberPII").val();
	var book=$("#bookPII").val();
	
	
	if(isNaN(price)||isNaN(number)){
		
		 if(isNaN(price)){
			 $('#pricePII').textbox('clear');	
			 $("#tipsPII").html("请输入正确参数!");
			 $("#tipsPII").css("color","red");
		 }
			 if(isNaN(number)){
				 $('#numberPII').textbox('clear');	
				 $("#tipsPII").html("请输入正确参数!");
				 $("#tipsPII").css("color","red");
			 }
			 
		 } else{
			 $('#dataPII').datagrid('appendRow',{
			 bookId: book ,
			 purchaseId:rowPro,
			 purchaseNum: number, 
			 purchasePrice: price
			 });
	
	 $('#bookPII').textbox('clear');	
	 $('#numberPII').textbox('clear');	
	 $('#pricePII').textbox('clear');
	 $("#tipsPII").html("添加成功!");
	 $("#tipsPII").css("color","green");
};

$('#tipsPII').delay(1000).hide(0);

}

function removePII(){
	var rows = $('#dataPII').datagrid('getSelections');

	console.log(rows);


	 if (rows) {
	        for(var i=0; i<rows.length;i++){
	            var rowIndex = $('#dataPII').datagrid('getRowIndex', rows[i]);
	            console.log(rowIndex);
	            $('#dataPII').datagrid('deleteRow', rowIndex);  
	        }
	     }
	$('#dataPII').datagrid('clearSelections');
	
}

//插入 项目
function postPIIbatch(){
	 $('#bookPII').textbox('clear');	
	 $('#numberPII').textbox('clear');	
	 $('#pricePII').textbox('clear');

	var data=$('#dataPII').datagrid('getData').rows;
	
	var jsonData =JSON.stringify(data);
	console.log(data);
	$.ajax({			
			url:$UniformResourceLocation+'purchaseItem/postPI',
			dataType:'json',
			async:false,
			data:{
				purchaseItemList:  jsonData,
			},
			success:function(data){
				
				
				//关窗口重新查询
				$('#PIInfo').dialog('close');
				$.messager.show({ // show error message
					title : '提示',
					msg : "应用成功",
				});
				doProSearch(rowPro);
			}
		});
	
	
	$('#dataPII').datagrid('clearSelections');
	
}






function delPI() {
	var selections = $('#dgPro').datagrid('getSelections');
	var array=new  Array();
	$.each(selections, function(i, item) {
		array.push(item["purchaseItemId"])
	})
	var arr=JSON.stringify(array);
	console.log(arr);
	if (arr) {
		$.messager.confirm('Confirm', '删除后不可恢复?', function(cfm) {
			if (cfm) {
				// $.post(url,data,success(data, textStatus,xhr),dataType)
				$.post('/bookstore/purchaseItem/delPIById', {
					idList : arr,
				},

				function(result) {
					if (result) {
						$('#dgPro').datagrid('clearSelections'); // reload the user data
						$.messager.show({ // show error message
							title : '提示消息',
							msg : result.msg
						});
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