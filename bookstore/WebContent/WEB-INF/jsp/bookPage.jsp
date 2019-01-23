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
<script type="text/javascript" src="<%=path%>/js/printAction.js"></script>
<script type="text/javascript" src="<%=path%>/js/setFirstPage.js"></script>
<script type="text/javascript" src="<%=path%>/js/myValidate.js"></script>
<script type="text/javascript" src="<%=path%>/js/validateDemo.js"></script>

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
	
	<!-- pagination : 是否可以分页  url：对应一个handler来获取数据    toolbar：使用一个div与之对应 ；
	rownumbers：在数据展示的时候是否显示行数；fitColumns：数据显示时是否根据内容调整每一列的占位；
	singleSelect：选择行数时是否只能单行选择-->
	<table id="dg" title="书的基本信息" class="easyui-datagrid" pagination="true"
		url="" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<!-- field ： 必须与返回json数据的字段(key)完全一致  ：json：total->总记录数 ;rows:数据 -->
				<th field="bookId" width="20" formatter="formatCellTooltip">书编号</th>
				<th field="bookName" width="50" formatter="formatCellTooltip">书名</th>
				<th field="authors" width="50" formatter="formatCellTooltip">作者</th>
				<th field="bookCategory" width="20" formatter="formatCellTooltip">类型</th>
				<th field="bookSalesPrice" width="20" formatter="formatCellTooltip">售价$</th>
				<th field="bookProfile" width="80" formatter="formatCellTooltip">简介</th>
				<th field="stockSales" width="20" formatter="formatCellTooltip">月销量/本</th>
				<th field="stockNum" width="20" formatter="formatCellTooltip">库存</th>
				<th field="publishName" width="30" formatter="formatCellTooltip">出版社</th>
				<th field="bookCoverImage" width="20" formatter="formatCellTooltip">路径</th>
				<th field="bookStatus" width="20" formatter="formatCellTooltip">状态</th>
				<th field="printId" width="20" formatter="formatCellTooltip">印刷编号</th>
			</tr>
		</thead>
	</table>
	
	<!-- 弹出框 -->
	<div id="bookAll" class="easyui-dialog"  style="height:300px;width:530px;"
	data-options="title:'印刷商信息',closed:true,toolbar:'#tb'">
		<table id="printTB" title="印刷商信息" class="easyui-datagrid" >
			<thead>
				<tr>
					<th field="printerName" width="22%">印刷厂名</th>
					<th field="printId" width="18%">印刷编号</th>
					<th field="printSize" width="18%">印刷开本</th>
					<th field="printDate" width="20%">印刷时间</th>
					<th field="printQuantity" width="13%">印刷数量</th>
					<th field="printBatch" width="13%">印刷批次</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="tb">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editPrint()">修改</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removePrint()">删除</a>
	</div>
	
	<div id="printInfo" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true" 
	buttons="#dlg-buttons1">
		<div class="ftitle">印刷厂信息</div>
		<!-- 添加时需要输入的信息 -->
		<form id="printFm" method="post">
			<div class="fitem">
				<label>印刷厂名:</label> 
				<input name="printerName" class="easyui-validatebox" placeholder="厂名" data-options="required:true,validType:'onlyChinese[1,50]'">
			</div>
			<div class="fitem">
				 <label>印刷编号:</label>
				<input name="printId" class="easyui-validatebox" placeholder="编号" data-options="required:true,validType:'number'">
			</div>
			<div class="fitem">
				<label>印刷开本:</label>
				<input name="printSize" class="easyui-validatebox" placeholder="开本/大小" data-options="required:true,validType:'number'">
			</div>
			<div class="fitem">
				<label>印刷日期:</label> 
				<input name="printDate" class="easyui-datebox" data-options="required:true">
			</div>
			<div class="fitem">
				<label>印刷数量:</label>
				<input name="printQuantity" class="easyui-validatebox" placeholder="数量" data-options="required:true,validType:'number'">
			</div>
			<div class="fitem">
				<label>印刷批次:</label> 
				<input name="printBatch" class="easyui-validatebox" placeholder="批次" data-options="required:true,validType:'number'">
			</div>
		</form>
	</div>
	
	<div id="dlg-buttons1">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePrint()">Save</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#printInfo').dialog('close')">Cancel</a>
	</div>
	
	<!-- 留言板弹出框 -->
	<div id="msg" class="easyui-window" title="查看评论" style="width:600px;height:400px"   
        data-options="iconCls:'icon-save', closed:true, modal:true">   
    	 <table id="msgTB" title="评论信息" class="easyui-datagrid" >
			<thead>
				<tr>
					<th field="messageId" width="30%">楼号</th>
					<th field="messageInfo" width="70%">评论内容</th>
				</tr>
			</thead>
		</table>
</div>
	
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
		<a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addBook()">添加书</a> &nbsp;
		<a class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBook()">修改书</a> &nbsp;
		<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeBook()">删除书</a>&nbsp;
		<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="getBookMsg()">查看评论</a>&nbsp;
		<a id="queryClear" class="easyui-linkbutton" iconCls="icon-remove" plain="true">清空查询条件</a>  
	</div>
	
	<div id="searchMenu" style="width: 120px">
		<div data-options="name:'bookId',iconCls:'icon-ok'">书号</div>
		<div data-options="name:'bookName',iconCls:'icon-ok'">书名</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 380px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">书的信息</div>
		<!-- 添加时需要输入的信息 -->
		<form id="fm" method="post">
			<div class="fitem">
				 <label>书编号:</label>
				<!-- readonly:true：必须输入 -->
				<input name="bookId" class="easyui-validatebox" data-options="validType:'number'">
			</div>
			<div class="fitem">
				<label>书名:</label> 
				<input name="bookName" class="easyui-validatebox" ><!-- data-options="required:true" -->
			</div>
			
			<div class="fitem" id="aId">
				<label>作者编号:</label> 
				<input name="authorId" class="easyui-validatebox" data-options="validType:'number'">
			</div>

			<div class="fitem" id="aName">
				<label>作者姓名:</label> 
				<input name="authorName" class="easyui-validatebox" data-options="validType:'onlyChinese[1,20]'">
			</div>
			
			<div class="fitem">
				<label>价格:</label> 
				<input name="bookSalesPrice" class="easyui-validatebox" data-options="validType:'number'">
			</div>
			
			<div class="fitem">
				<label>类型:</label> 
				<select id="bookCategory" class="easyui-combobox" name="bookCategory" style="width: 75px;">
					<option value="名著">名著</option>
					<option value="烹饪">烹饪</option>
					<option value="科幻">科幻</option>
					<option value="少儿">少儿</option>
					<option value="喜剧">喜剧</option>
					<option value="电脑">电脑</option>
				</select> 
			</div>
			
			<div class="fitem">
				<label>状态:</label> 
				<select id="bookStatus1" class="easyui-combobox" name="bookStatus" style="width: 75px;">
					<option value="在售">在售</option>
					<option value="下架">下架</option>
				</select> 
			</div>
			
			<div class="fitem"><!-- required:true, -->
				<label>出版商名:</label> 
				<input name="publishName" class="easyui-validatebox" data-options="validType:'rang[1,100000]'">
			</div>
			
			<div class="fitem">
				<label>图片路径:</label> 
				<input name="bookCoverImage" class="easyui-validatebox" data-options="validType:'rang[1,100000]'">
			</div>
			
			<div class="fitem">
				<label>印刷商编号:</label>
				<input name="printId" class="easyui-validatebox" data-options="validType:'number'">
			</div>
			
			<div class="fitem">
				<label>简介:</label> 
				<input name="bookProfile" class="easyui-validatebox" value="">
			</div>
			
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveBook()">保存</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
	
</body>
</html>