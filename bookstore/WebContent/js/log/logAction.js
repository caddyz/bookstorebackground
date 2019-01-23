var url = "";// 定义一个全局变量传给id为fm的form提交事件


let $operateType='';
let $dept='';
let $startTime='';
let $endTime='';

let $UniformResourceLocation='http://'+window.location.host+'/bookstore/';


	function doSearch(){ 
	
		$.ajax({			
			url:$UniformResourceLocation+'log/dynamicGetLog',
			dataType:'json',
			async:false,
			data:{
				adminId:$("#adminId").val(),
				operateType:$operateType,
				operate:$("#operate").val(),
				requestIp:$("#requestIp").val(),
				dept:$dept,
				startTime:$startTime,
				endTime:$endTime,
				currentPage			:1,
				pageSize			:10
			},
			success:function(data){
				transferType(data.value.rows);
				transferOperator(data.value.rows);
				$("#dg").datagrid('loadData',data.value);				
				console.log(data);
			}
		});
		
   
	}
	

	
	
	
	$(function(){	
		
		$('#dg').datagrid({    
	 		 columns:[[    
	 		 {field:'logId',hidden:true},    
	 		 {field:'operator',title:'操作者',width:40 ,align:'left'},    
	 		 {field:'operateType',title:'类型',width:50,align:'center'}   ,
	 		 {field:'operate',title:'操作',width:100,align:'center'}    ,
	 		 {field:'detail',title:'细节',width:300,align:'left',hidden:true,
	 			 formatter: function(value,row,index){
						if (row.detail){
							return "&emsp;"+row.detail;
						} else {
							return '&emsp;无细节';
						}
					}
	 		 
	 		 }    ,
	 		 {field:'requestIp',title:'ip',width:50,align:'center'}  ,
	 		 {field:'logTime',title:'时间',width:100,align:'center'}  ,
	 		 {field:'dept',title:'部门',width:60,align:'center'}  ,
	 		 ]] ,
	 		 pagination:true,
	 		 pageList:[5,10,20],
	 		 fitColumns:true,
	 		 striped:true,
	 		 rownumbers:true,
	 		 singleSelect:true,
	 		 nowrap:false
	 		 });   
		
		doSearch();	
		

	
		
	
		var pager = $('#dg').datagrid('getPager');	
		pager.pagination({
			pageSize:10,
			pageNumber:1,
			showPageList:true,
			onSelectPage:function(pageNumber, pageSize){
				$.ajax({
					url:$UniformResourceLocation+'log/dynamicGetLog',
					dataType:'json',
					async:false,
					data:{						
						adminId:$("#adminId").val(),
						operateType:$operateType,
						operate:$("#operate").val(),
						requestIp:$("#requestIp").val(),
						dept:$dept,
						startTime:$startTime,
						endTime:$endTime,
						currentPage			:pageNumber,
						pageSize			:pageSize,
					}, 
					success:function(data){
						transferType(data.value.rows);
						 transferOperator(data.value.rows);
						$("#dg").datagrid('loadData',data.value);
						console.log(data);
					}
				});		
			}			
		});
	});	
	

	
	
	$(function(){
		
		
//		设置查询时间格式
		$('#startTime').datebox({
		    onSelect: function(date){
		        $startTime = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    }
		});	
		$('#endTime').datebox({
		    onSelect: function(date){
		        $endTime = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    }
		});	

//查询选框中数据
		$('#dept').combobox({
			url:$UniformResourceLocation+'employee/allDept',
			valueField : 'id',
			textField : 'text',
			onSelect : function(record) {
				$dept = record.text;
			}
		});
	
		$('#operateType').combobox({
			onSelect: function(record){
				alert("45844");
				$operateType = record.text;
				console.log(record);
			}
		});		
	
	
	})
	
	function transferType(rows) {
		$.each(rows, function(i, item) {
		
			switch (item["operateType"]) {
			case -1:
				item["operateType"] = "错&emsp;&emsp;&emsp;误";
				break;
			case 0:
				item["operateType"] = "未&emsp;&emsp;&emsp;知";
				break;
			case 1:
				item["operateType"] = "登陆与注销";
				break;
			case 2:
				item["operateType"] = "数据库操作";
				break;
			case 3:
				item["operateType"] = "日&emsp;&emsp;&emsp;志";
				break;
			default:
				item["operateType"] = "未&emsp;&emsp;&emsp;知";
				break;
			}
		})
	}
	
	function transferOperator(rows) {
		$.each(rows, function(i, item) {
			item["operator"] = item["operator"].emp.empName;
		})
	}

	//获取date的前i天日期
	function getAgo(i,date){
		var lastDate = new Date(date - 1000 * 60 * 60 * 24 * i);
		console.log(lastDate);
		var year = lastDate.getFullYear();
		var month = lastDate.getMonth()+1;
		var day = lastDate.getDate();
		var LDate = year + "-" + (month < 10 ? "0" + month : month) + "-"+(day < 10 ? "0" + day : day);
		return LDate;
	}
	
	//获取当前日期的字符串格式
	function todayStr(){
		var date = new Date();                //得到当前日期原始模式
		var year = date.getFullYear();     //得到当前日期年份
		var month = date.getMonth() + 1;   //得到当前日期月份（注意： getMonth()方法一月为 0, 二月为 1, 以此类推。）
		var day = date.getDate();            //得到当前某日日期（1-31）
		month = (month<10 ? "0"+month:month);  //10月以下的月份自动加0
		var today = year+"-"+month + "-" + day;
		return today;
	}
	
let $start='';
let $end='';
var now =new Date();

function confirm(){
			
			var choose=$('input:radio[name="choose"]:checked').val();
			
			switch (choose) {
			case "custom":
				
		
				$('#customStart').datebox({
					onSelect: function(date){
					
						$start = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
					}
				});	
			
				$('#customEnd').datebox({
					onSelect: function(date){
					
						$end = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
					}
				});
			
			
				console.log($end +""+ $start);
			
				break;
			case "aWeekAgo":
				$start= getAgo(7,now);
				$end=todayStr();
				break;
			case "aMonthAgo":
				$start= getAgo(30,now);
				$end=todayStr();
				break;
			case "threeMonthAgo":
				$start= getAgo(90,now);
				$end=todayStr();
				break;
			default:
				return alert("请选择删除的时间！");
			}
			
			console.log($end +""+ $start);
			if($end!=''&$start!=''){
				delLog();
			}
			
		};
			
		
		function delLog(){

			$.ajax({			
				url:$UniformResourceLocation+'log/delLogByTime',
				dataType:'json',
				async:false,
				data:{
					startTime:$start,
					endTime:$end,
				},
				success:function(data){
					console.log(data)
					$('#delLog').dialog('close');
				
					$.messager.show({ // show error message
						title : '提示消息',
						msg : data.msg
					});
					
					doSearch();
					
					}
					
				})
	};


function openDelDg() {
	$('#delLog').dialog('open').dialog('setTitle','删除日志');
	
}