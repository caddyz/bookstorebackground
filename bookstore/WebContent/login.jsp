<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>书城OA系统登录</title>
<!-- <link rel="stylesheet" type="text/css" href="css/login.css" /> -->
<link rel="shortcut icon" href="images/bookstore.ico" type="image/x-icon" />
<style type="text/css">
	*{
    margin: 0;
    padding: 0;
}
body{
	background:skyblue;
	/* background: url("images/bg.jpg"); 
	background-size: cover;
	background-repeat:no-repeat;*/
}

/*    .main{
    background : white
    margin-top: 50px;
    height: 400px;
    overflow: hidden;
    
}  */
.login{
    background-color: #ffffff;
    margin: auto;
    margin-top: 40px;
    width: 400px;
    height: 360px;
    opacity: 0.8;
    border-radius: 10%;
    /* display:none; */
}
.title{
    text-align: center;
    border-bottom: 1px solid #ddd;
    font-size: 18px;
    font-weight: 700;
    color: #666;
    line-height: 50px;
}

form{
    height: 200px;
}
.userName,.psw{
    margin: auto;
    display: block;
    line-height: 40px;
    margin-top: 20px;
    width: 360px;
    outline: none;
}
.save{
	width:150px;
	
	margin:20px auto;
	
}
.loginBtn{
    background-color: #e43a3d;
    color: #ffffff;
    font-size: 20px;
    user-select: none;
    cursor: pointer;
    text-align: center;
    text-decoration: none;
    display: block;
    margin: auto;
    margin-top: 20px;
    line-height: 40px;
    width: 360px;
}
.other{
	line-height:50px;
	
}
.other a{
    text-decoration: none;
    font-size: 12px;
    float: right;
    padding-right: 20px;
}
.error{
	text-align : center;
	position: relative;
    margin:10px auto;
    margin-top:10px;
    font-size:10px;
    width:140px;
    /*border: 1px solid red;*/
    /*background: #fde9e9;*/
    color: red;
    
    text-align:center;
   /* padding: 4px 0 4px 4px;*/
}
	
</style>
</head>
<body>

    <div id="main" class="main">
       <div class="login">
           <div class="title">用户登录</div>
           <div class="error">${message}</div>
           <div class="loginForm">
               <form action="loginCtrl/check" method="post" id="form1">
                   <input class="userName" type="text" name="adminName" placeholder="请输入用户名"/>
                   <input class="psw" type="password" name="psw" placeholder="请输入密码"/>
                   <div class="loginBtn" onclick="sb()">登录</div>

               </form>
           </div>

       </div>

    </div>
</body>
<script type="text/javascript" src="/bookstore/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript">
 var userName = document.getElementsByClassName("userName")[0];
var psw = document.getElementsByClassName("psw")[0];
var error = document.getElementsByClassName("error")[0];
/*
//var message = "";
var form1 = document.getElementById("form1");
if(message != "" && message != null){
	error.innerHTML = message;
	error.style.display = "block";
}
*/
 $(function(){
	$(".login").fadeIn("slow");
}) 
function sb(){
	if(userName.value == "" || psw.value == ""){
		error.innerHTML = "用户名和密码不能为空";
		//error.style.display = "block";
	}else{
		form1.submit();
	}
}
/*
 $(function(){
	$(".error").text(message);
})  */

</script>
</html>