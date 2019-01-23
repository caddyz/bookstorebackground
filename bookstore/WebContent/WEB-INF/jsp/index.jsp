<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>书城OA系统</title>
<link rel="shortcut icon" href="<%=path %>/images/bookstore.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/default.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/js/themes/icon.css" />
<script type="text/javascript" src="<%=path %>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript" src="<%=path %>/js/menuAction.js?v=<%=Math.random()%>"> </script>
<script type="text/javascript">
	var _menus = {
		"menus" : [
			{
				"menuid" : "0",				
				"icon" : "icon-sys",
				"menuname" : "个人中心",
				"menus" : [
					{
						"menuid" : "01",
						"menuname" : "个人信息",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>mypage/toMypage"
					},
					
					{
						"menuid" : "02",
						"menuname" : "操作日志",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>log/myLog"
					},
				]
			},
			{
				"menuid" : "1",				// 与后端permissionId对应
				"icon" : "icon-sys",
				"menuname" : "营销策划",
				"menus" : [
					{
						"menuid" : "11",
						"menuname" : "营销活动",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>activity/pagination"
					},
					{
						"menuid" : "12",
						"menuname" : "优惠券库",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>coupon/pagination"
					},
					{
						"menuid" : "13",
						"menuname" : "推荐商品",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>themeController/sendThemePage"
					},
					{
						"menuid" : "14",
						"menuname" : "历史活动记录",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>activity/historyActivity"
					},
				]
			}, {
				"menuid" : "2",
				"icon" : "icon-sys",
				"menuname" : "采购部门",
				"menus" : [
					{
					"menuid" : "21",
					"menuname" : "合作伙伴",
					"icon" : "icon-nav",
					"url" : "<%= basePath %>supplier/index"
					},
					{
						"menuid" : "22",
						"menuname" : "我的采购",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>purchase/myIndex"
					},
					
					{
						"menuid" : "23",
						"menuname" : "采购查询",
						"icon" : "icon-nav",
						"url" :  "<%= basePath %>purchase/index"
					},
					{
						"menuid" : "24",
						"menuname" : "商品信息",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>bookController/sendBookPage"
						},
						{
							"menuid" : "36",
							"menuname" : "印刷厂家",
							"icon" : "icon-nav",
							"url" : "<%= basePath %>printController/sendPrintPage"
						},
				
					
				]
			}, 
			{
				"menuid" : "3",
				"icon" : "icon-sys",
				"menuname" : "仓库管理",
				"menus" : 
					[{
						"menuid" : "31",
						"menuname" : "库存管理",
						"icon" : "icon-nav",
						"url" :  "<%= basePath %>stockOperate/index"
			}, 
						{
							"menuid" : "32",
							"menuname" : "库存查詢",
							"icon" : "icon-nav",
							"url" :  "<%= basePath %>stock/index"
				}]
					
			},
			
			
			
			{
				"menuid" : "4",
				"icon" : "icon-sys",
				"menuname" : "人力资源",
				"menus" : 
					[ 
						{
					"menuid" : "41",
					"menuname" : "员工信息",
					"icon" : "icon-nav",
					"url" : "<%= basePath %>employee/pagination"
				},
					{
						"menuid" : "42",
						"menuname" : "职位信息",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>job/pagination"
					},
					{
						"menuid" : "43",
						"menuname" : "考勤信息",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>attendance/pagination"
					},
					{
						"menuid" : "44",
						"menuname" : "薪资结算",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>salary/pagination"
					}
				]
			}, {
				"menuid" : "5",
				"icon" : "icon-sys",
				"menuname" : "订单信息",
				"menus" : [
					
					{
						"menuid" : "53",
						"menuname" : "订单管理",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>orderController/sendOrderPage"
					}
				]
			}, {
				"menuid" : "6",
				"icon" : "icon-sys",
				"menuname" : "财务管理",
				"menus" : [
					{
						"menuid" : "61",
						"menuname" : "支收管理",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>financial/pagination"
					},
					{
						"menuid" : "62",
						"menuname" : "薪资发放",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>financial/salaryIssue"
					},
					{
						"menuid" : "63",
						"menuname" : "采购申请",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>financial/purchaseApply"
					},
					{
						"menuid" : "64",
						"menuname" : "订单入账",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>financial/orderEntry"
					}
				]
			},
			{
				"menuid" : "7",
				"icon" : "icon-sys",
				"menuname" : "后台管理",
				"menus" : 
					 [
							{
								"menuid" : "71",
								"menuname" : "账户管理",
								"icon" : "icon-nav",
								"url" : "<%= basePath %>admin/pagination"
							}
					,
					{
						"menuid" : "72",
						"menuname" : "日志管理",
						"icon" : "icon-nav",
						"url" : "<%= basePath %>log/logAdmin"
					}
				]
			}
		]
};
	
	
	
	
	
	//设置登录窗口
	function openPwd() {
		$('#w').window({
			title : '修改密码',
			width : 300,
			modal : true,
			shadow : true,
			closed : true,
			height : 160,
			resizable : false
		});
	}
	//关闭登录窗口
	function closePwd() {
		$('#w').window('close');
	}
	//修改密码
	function serverLogin() {
		var $newpass = $('#txtNewPass');
		var $rePass = $('#txtRePass');

		if ($newpass.val() == '') {
			msgShow('系统提示', '请输入密码！', 'warning');
			return false;
		}
		if ($rePass.val() == '') {
			msgShow('系统提示', '请在一次输入密码！', 'warning');
			return false;
		}
		if ($newpass.val() != $rePass.val()) {
			msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
			return false;
		}
		$.post('/bookstore/admin/updateSelf',{newpass:$newpass.val()}, function(data) {
			msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + data.msg, 'info');
			$newpass.val('');
			$rePass.val('');
			closePwd()
		})
	}
	$(function() {

		openPwd();

		$('#editpass').click(function() {
			$('#w').window('open');
		});

		$('#btnEp').click(function() {
			serverLogin();
		})

		$('#btnCancel').click(function() {
			closePwd();
		})

		$('#loginOut').click(function() {
			$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

				if (r) {
					location.href = '<%= basePath %>loginCtrl/loginOut';
				}
			});
		})
	});
</script>
<script type="text/javascript">
var $admin = {};

$(function(){
	var date = new Date();                //得到当前日期原始模式
	var year = date.getFullYear();     //得到当前日期年份
	var month = date.getMonth() + 1;   //得到当前日期月份（注意： getMonth()方法一月为 0, 二月为 1, 以此类推。）
	var day = date.getDate();            //得到当前某日日期（1-31）
	month = (month<10 ? "0"+month:month);  //10月以下的月份自动加0
	var newdate = year+"年"+month + "月" + day +"日";
		$("#time").html(newdate);
	
	
});



</script>


</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div
			style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
			<img src="<%=path %>/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
		<span style="float:right; padding-right:20px;" class="head">
        <span id="time" ></span>  &nbsp;欢迎
			<span style="color:red"> &emsp;${admin.emp.empName }&emsp;</span> <a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut">安全退出</a>
		</span> <span style="padding-left:10px; font-size: 16px; "><img src="<%=path %>/images/blocks.gif" width="20" height="20" align="absmiddle" />
			书城OA系统</span>
	</div>
	<!-- 底部div -->
	<div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
		<div class="footer"> BookStore  Corporation</div>
	</div>
	<!-- 左边导航菜单 -->
	<div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
		<div id="nav" class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->
		</div>
	</div>
	<!-- 中心面板 -->
	<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<!-- html的 -->
			<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; ">
				加油！
			</div>
		</div>
	</div>
	<!--修改密码窗口-->
	<div id="w" class="easyui-window" title="修改密码" collapsible="false"
		minimizable="false" maximizable="false" icon="icon-save"
		style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>新密码：</td>
						<td><input id="txtNewPass" type="password" class="txt01" /></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="txtRePass" type="password" class="txt01" /></td>
					</tr>
				</table>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
					href="javascript:void(0)"> 确定</a> <a id="btnCancel"
					class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:void(0)">取消</a>
			</div>
		</div>
	</div>
	<!-- 右击菜单 -->
	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
</body>
</html>