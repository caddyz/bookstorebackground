<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.print("basePath : " + basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/jquery-easyui-1.4.5/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/jquery-easyui-1.4.5/demo/demo.css">
<link rel="stylesheet" href="<%=path%>/css/wsk.css">
<link rel="stylesheet" href="<%=path%>/css/tips.css">
<script type="text/javascript" src="<%=path%>/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.datagrid.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.dialog.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=path%>/jquery-easyui-1.4.5/plugins/jquery.messager.js"></script>

<script type="text/javascript" src="<%=path%>/js/stock/stockAction.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/purchase/purchaseValidate.js"></script>
<script type="text/javascript" src="<%=path%>/js/tips.js"></script>
<script type="text/javascript" src="<%=path%>/js/datagridTools.js"></script>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

#left {
	float: left;
	width: 40%;
}

#middle {
	width: 10%;
	float: left;
}

#middle div {
	margin-bottom: 100px;
}

#right {
	top: 20px;
	padding: 10px;
	margin: 10px;
	float: left;
	width: 40%;
}

.arrow {
	margin-left: 30px; width : 100px;
	height: 80px;
	width: 80px;
}
#voucher div{

float:left;

}
#postStockDiv{
position:relative;
bottom:25px;
margin-left:40px;

}
</style>


<title>入库管理</title>

</head>
<body>
	<div id="left">
		<table id="piDg" title="待入库项目" class="easyui-datagrid" onClickRow="ClickRow" toolbar="#toolBar">
		</table>
	</div>

	<div id="middle">
		<div></div>
		<img class="arrow" src="<%=path%>/images/rightForward.jpg" onclick="addStockItem()" /> <br />
			 <img class="arrow"src="<%=path%>/images/leftForward.jpg" onclick="removeStockItem()" />
	</div>
	<div id="right">
		<div id="information" data-options="iconCls:'icon-ok',tools:'#postStock'">
			
				<div>
				
				
				
				
				</div>
			
	<div class="ftitle">交货凭证	<span id="tips"> </span></div>
		<div class="fitem">
			<label>仓库选择</label><input id="storeHouse" class="easyui-combobox" style="width: 120px"  />
			</div>
		<form id="stockImg" method="post" enctype="multipart/form-data">  
		<div id="voucher">
		<div>
			<div class="fitem">
			<label>凭证添加</label> <input id="file"class="easyui-filebox" name="file" style="width:125px" buttonText="添加凭证">
			</div>
		</div> 
		<div id="postStockDiv">
			<input id="postStock" type="submit"  onclick="postStockBatch()"   style="width: 120px ; height: 50px" value="入库">
		</div>
		</div>
		</form> 
	
		</div>
		
		<table id="stockItemDg" title="待入库项目" class="easyui-datagrid"
			onClickRow="ClickRow" >

		</table>



	</div>




	<div id="toolBar">
	
			
<input id="searchBookName" class="easyui-searchbox" style="width: 150px" data-options="searcher:doProSearch,prompt:'请输入书名关键字'"></input>
	
			<input id="searchBatch" type="hidden" style="width: 100px" /> &emsp; &emsp;
			<input type="checkbox"  onclick="checkAll(this,'#piDg')">全选
	</div>


	<div id="dlg-buttons"></div>


</body>


</html>