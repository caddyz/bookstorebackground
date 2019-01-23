<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<%=path%>/jquery-easyui-1.4.5/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/jquery-easyui-1.4.5/demo/demo.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/tips.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bookPage.css">
<script type="text/javascript" src="<%=path%>/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>
<script type="text/javascript" src="<%=path%>/js/bookAction.js?s<%=Math.random() %>>"></script>
<script type="text/javascript" src="<%=path%>/js/setFirstPage.js"></script>
<script type="text/javascript" src="<%=path%>/js/myValidate.js"></script>
<script type="text/javascript" src="<%=path%>/js/validateDemo.js"></script>
<script type="text/javascript" src="<%=path%>/js/purchase/purchaseItemAction.js"></script>

<title>书城后台管理</title>

<script type="text/javascript"> 

	// 全局变量
	let $category = "";
	let $value = "";
	let $publishName = "";
	let $bookStatus = "";
	let $authorName = "";
	let $bottomPrice = "";
	let $topPrice = "";
	
	// 点击查询执行的方法
	function doSearch(value, name){ 
		
			setFirstPage("#dg");
		
			$bottomPrice = $("#bottomPrice").val(),
			$topPrice = $("#topPrice").val()
			$value = value;
			
			$.ajax({
				url:'<%=basePath%>bookController/getBook/1/10', 
				dataType : "json",
				async : false,
				data : {
					value : 				$value,
					authorName:		$authorName,
					bookCategory:	$category, 
					topPrice : 			$topPrice,
					bottomPrice : 	$bottomPrice,
					publishName:	$publishName,
					bookStatus:		$bookStatus
				},
				success: function(data){
					$("#dg").datagrid('loadData', data.value);
				}
			});
	}
	
	// 分页的操作 
	$(function(){
		var pager = $('#dg').datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			showPageList:true,
			onSelectPage:function(pageNumber, pageSize){
			// $(this).pagination('loading'); 
			$.ajax({
				url:'<%=basePath%>bookController/getBook/'+pageNumber+'/'+pageSize,
				dataType : 'json',
				async : false,  // 同步
				data : {
					value : 				$value,
					authorName:		$authorName,
					topPrice : 			$topPrice,
					bottomPrice : 	$bottomPrice,
					publishName:	$publishName,
					bookCategory : $category,
					bookStatus:		$bookStatus
				},
				success : function(data){
					$("#dg").datagrid('loadData', data.value);
				}
		  });
		}
	});
});
	
	// 书的类型
	$(function(){
		$("#category").combobox({
			onSelect: function(record){
				$category = record.text;
			}
		}) 
		$('#category').combobox({    
		    url: '<%=basePath %>bookController/getCategpry', 
		    valueField:'id',         
		    textField:'text'   
		});  
	});
	
	// 书的状态
	$(function(){
		$("#bookStatus").combobox({
			onSelect: function(record){
				$bookStatus = record.text;
			}
		}) 
		$('#bookStatus').combobox({    
		    url: '<%=basePath %>bookController/getBookStatus', 
		    valueField:'id',         
		    textField:'text'   
		});  
	});
	
	// 获取印刷商
	$(function(){
		$("#publishName").combobox({
			onSelect: function(record){
				$publishName = record.text;
			}
		}) 
		$('#publishName').combobox({    
		    url: '<%=basePath %>bookController/getPublishName', 
		    valueField:'id',         
		    textField:'text'   
		});  
	});
	
	// 清空查询条件
	$(function(){
		$("#queryClear").bind("click", function(){
			$category = "";
			$value = "";
			$publishName = "";
			$bookStatus = "";
			$authorName = "";
			$bottomPrice = "";
			$topPrice = "";
			$("input").val('');
		})
	})
	
	// 获取所有作者
	$(function() {
		$('#author').combogrid({
			panelWidth : 210,
			idField : 'authorId',
			textField : 'authorName',
			url : 'getAuthor',
			columns : [ [ {
				field : 'authorId',
				title : '作者编号',
				width : 70
			}, {
				field : 'authorName',
				title : '作者姓名',
				width : 140
			}, ] ],
			onSelect : function(data, value) {
				$authorName = value.authorName;
			}
		});
	})
	
	
</script>

<script type="text/javascript">   
        //格式化单元格提示信息  
        function formatCellTooltip(value){  
            return "<span title='双击查看印刷商信息'>" + value + "</span>";  
        }  
</script>  
  
</head>
<body>

 <table>
        <tr><td>
        <table id="dg" title="书的基本信息" class="easyui-datagrid" pagination="true"
		url="" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="bookId" width="20"  hidden=true>书编号</th>
				<th field="bookName" width="50" >书名</th>
				<th field="authors" width="50" >作者</th>
				<th field="bookSalesPrice" width="20" >售价</th>
				<th field="stockNum" width="20" >库存</th>
				<th field="publishName" width="30">出版社</th>
			</tr>
		</thead>
	</table>
	</td>
            <td>
            </td>
            <td><table  id="dgPro" class="easyui-datagrid selectedGrid" ></table></td>
        </tr>
    </table>


	


	
	<div id="toolbar">	
	<br/>
		<a class="easyui-linkbutton" onclick="doSearch('', '')" data-options="iconCls:'icon-search'">查询所有书</a>
		&nbsp;&nbsp; 
		<!-- searcher：点击查询标识的时候要执行的方法 -->
		<input id="ss" class="easyui-searchbox" style="width: 200px" data-options="searcher:doSearch,prompt:'请输入关键字',menu:'#searchMenu'"></input>
		&nbsp;&nbsp;
		 
		价格区间<input id="bottomPrice" type="text" class="easyui-numberbox" value="" data-options="min:0,precision:2" />~ 
						<input id="topPrice" type="text" class="easyui-numberbox" value="" data-options="min:0,precision:2" /> &nbsp;&nbsp;
		状态		<input id="bookStatus" name="dept" value="" /> &nbsp;&nbsp;
		类型		<input id="category" name="dept" value="" /> &nbsp;&nbsp;
		作者		<input id="author" name="dept" value="" style="width:120px"/>  &nbsp;&nbsp;
		出版社	<input id="publishName" name="dept" style="width: 100px;" />
						<br/>
						<br/>

		<!-- 按键 -->
		<a id="queryClear" class="easyui-linkbutton" iconCls="icon-remove" plain="true">清空查询条件</a>  
	</div>
	
	<div id="searchMenu" style="width: 120px">
		<div data-options="name:'bookId',iconCls:'icon-ok'">书号</div>
		<div data-options="name:'bookName',iconCls:'icon-ok'">书名</div>
	</div>

</body>
</html>