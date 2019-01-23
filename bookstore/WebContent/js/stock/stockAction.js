



let $UniformResourceLocation='http://'+window.location.host+'/bookstore/';



//查詢方法
function doProSearch(){
	$currentPagePro=1;
	$pageSizePro=15;
		$.ajax({			
			url:$UniformResourceLocation+'purchaseItem/dynamicGetPI3',
			dataType:'json',
			async:false,
			data:{
				purchaseBatch:  $("#searchBatch").val(),
				bookName:  $("#searchBookName").val(),
				currentPage	:$currentPagePro,
				pageSize:$pageSizePro,
			},
			success:function(data){
				$("#piDg").datagrid('loadData',data.value);				
				console.log(data);
			}
		});
	
	}

//将数据存入数据苦衷
function putIn(){
	$.ajax({			
		url:$UniformResourceLocation+'stockOperate/postStockBatch',
		dataType:'json',
		async:false,
		data:{
			purchaseBatch:  $("#searchBatch").val(),
			bookName:  $("#searchBookName").val(),
			currentPage	:$currentPagePro,
			pageSize:$pageSizePro,
		},
		success:function(data){
			$("#piDg").datagrid('loadData',data.value);				
			console.log(data);
		}
	});
	
}


//窗口中加载row 数据 并且初始化第一页 数据


$(function(){

	$('#information').panel({    
		  height:150,    
		  title: '入库清单',   
		  collapsible:true,
		  
		});  
	
	$('#stockImg').form({    
	    url:$UniformResourceLocation+"stockOperate/upload",    
	    success:function(data){    
	    }    
	});  
	
	
	$('#piDg').datagrid({ 
		 columns:[[    
		 {field:'purchaseItemId',title:'采购编号',align:'center',
			 formatter: function(value,row,index){
					if (row.purchase){
						return row.purchase.purchaseBatch+'-'+row.purchaseItemId;
					} else {
						return '未知';
					}
		 }    
		 },  
		 {field:'book',title:'采购内容',width:100,
			 formatter: function(value,row,index){
					if (row.book){
						return row.book.bookName;
					} else {
						return '未知';
					}
				}
		 },    
		 
		 {field:'purchaseNum',title:'采购数量',width:30,align:'center'}   
		
		 ]] ,
		 pagination:true,
 		 pageList:[20,40,60],
 		 fitColumns:true,
 		 striped:true,
 		 toolbar:$("#toolBar"),
 		 height:500,
 		
	});
	
	doProSearch();
	
	var pagerPro = $('#piDg').datagrid('getPager');	
	pagerPro.pagination({
		pageSize:20,
		pageNumber:1,
		showPageList:true,
		onSelectPage:function(pageNumber, pageSize){
			$.ajax({
				url:$UniformResourceLocation+'purchaseItem/dynamicGetPI3',
				dataType:'json',
				async:false,
				data:{						
					purchaseBatch:  $("#searchBatch").val(),
					bookName:  $("#searchBookName").val(),
					currentPage			:pageNumber,
					pageSize			:pageSize,
				}, 
				success:function(data){
					$("#piDg").datagrid('loadData',data.value);
					console.log(data);
				}
			});		
		}	
	});

	
	
	
	//库存选框
	$('#stockItemDg').datagrid({  
		title:'添加库存',	
 		 columns:[[
 			 {field:'purchaseItemId',title:'采购编号',width:100,align:'center',
 				 formatter: function(value,row,index){
 					if (row.purchase){
 						return row.purchase.purchaseBatch+'-'+row.purchaseItemId;
 					} else {
 						return '未知';
 					}
 		 } 	 
 			 }  ,  
 			 
 			 {field:'book',title:'库存内容',width:200,align:'left',
 			formatter: function(value,row,index){
				if (row.book){
					return row.book.bookName;
				} else {
					return '未知';
				}
			}
 		 }   ,
 		 {field:'purchaseNum',title:'入库数量',width:100,align:'center'}    
 		 ]],
 		 fitColumns:true,
 		 striped:true,
 		 rownumbers:true,
 		 singleSelect:false
 		 });  

	
	$('#storeHouse').combobox({
		url:$UniformResourceLocation+'storeHouse/getComBox',
		valueField : 'id',
		textField : 'text',
		onSelect : function(record) {
			$storeHouse = record.id;
		}
	});

})





 
//这是一个用来将数据从采购框复制到库存项目的中间变量
let $stockItem=new  Array();

function addStockItem(){
	var row=$('#piDg').datagrid('getSelections');  
	console.log(row);
	$stockItem=$stockItem.concat(row);
	
	
	$("#stockItemDg").datagrid('loadData',$stockItem);
	//$stockItem.length=0;
	 if (row) {
	        for(var i=0; i<row.length;i++){
	            var rowIndex = $('#piDg').datagrid('getRowIndex', row[i]);
	            $('#piDg').datagrid('deleteRow', rowIndex);  
	        }
	     }
	$('#piDg').datagrid('clearSelections');
	
}

function removeStockItem(){
	
	var row=$('#stockItemDg').datagrid('getSelections');   
	console.log("库存框选中的数据");
	console.log(row);
	 if (row) {
	        for(var i=0; i<row.length;i++){
	            var rowIndex = $('#stockItemDg').datagrid('getRowIndex', row[i]);
	            $('#stockItemDg').datagrid('deleteRow', rowIndex);  
	        }
	     }
	$('#stockItemDg').datagrid('clearSelections');
	doProSearch();
}

//构造器
function StockPojo(bookId,stockNum,purchaseItemId,storeHouseId) {
    this.bookId = bookId;
    this.stockNum = stockNum;
    this.purchaseItemId = purchaseItemId;
    this.storeHouseId = storeHouseId;
}


function postStockBatch(){
	
	var file=$("#file").val()
	console.log(file);
	
	
	var rows=$('#stockItemDg').datagrid('getData').rows;
	console.log(data);
	var data=new  Array();
	var storeHouse=$("#storeHouse").val();
	console.log(storeHouse)
	$.each(rows, function(i, item) {
		
		var stockPojo= new  StockPojo(item.book.bookId,item.purchaseNum,item.purchaseItemId,storeHouse);
			data.push(stockPojo);

	
	});
	var jsonData =JSON.stringify(data);
	console.log(data);
	console.log(jsonData);
	
	$.messager.confirm('确认','清点完成确认入库？',function(r){    
	    if (r){    
	    	$.ajax({			
				url:$UniformResourceLocation+'stockOperate/postStockBatch',
				dataType:'json',
				async:false,
				data:{
					PIList:  jsonData,
				},
				success:function(data){
					$('#PIInfo').dialog('close');	
					$('#stockItemDg').datagrid('loadData', { total: 0, rows: [] });
					console.log(data);
				}
			});
	    }    
	});
	
	//清除数据


	
	
	
}

 