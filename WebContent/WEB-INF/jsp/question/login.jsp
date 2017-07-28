<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.augmentum.oes.Oes"%>
<!DOCTYPE html >
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link href="<%=request.getContextPath() %>/static/reset.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath() %>/static/login.css" rel="stylesheet" type="text/css" /> 
    <script>
        function login() {
            var LoginObj=document.getElementById("loginForm");
            var isSubmitForm = true;
            var userNameObj = document.getElementById("userName");
            var userNameWrong = document.getElementById("name_worng_img");
            var passwordWorng = document.getElementById("password_worng_img");
            
            var userNameValue = userNameObj.value;
            var errorMsg = "username in requirzed!";
            var errorMsgObj = document.getElementById("errorMsg");
            
            var userNameIsNull = false;
            var passwordIsNull = false;
            if (!userNameValue) {
                userNameIsNull = true;
                errorMsg = "username in requirzed!";
                userNameObj.style.border = "1px solid #EB340A";
                userNameWrong.style.visibility = "visible";
                isSubmitForm = false;
            } else {
                userNameObj.style.cssText = "border:1px solid #858585";
                
            }

            var passwordObj = document.getElementById("password");
            var passwordValue = passwordObj.value;
            
            if (!passwordValue) {
                passwordIsNull = true;
                errorMsg = "password in requirzed!";
                passwordObj.style.border = "1px solid #EB340A";
                passwordWorng.style.visibility = "visible";
                isSubmitForm = false;
            } else {
                userNameObj.style.cssText = "border:1px solid #858585";
            }
            if(userNameIsNull && passwordIsNull) {
                errorMsg = "username and password in requirzed!";
                passwordWorng.style.visibility = "visible";
            }
            if (!isSubmitForm) {
                errorMsgObj.innerHTML = errorMsg;
               
            }
            if (isSubmitForm) {
                LoginObj.submit();
            }
        }
    </script>

    <script>
    function change(){
        var img = document.getElementById("img");
        if(img.getAttribute("src") == "<%=request.getContextPath() %>/static/images/BTN_RemeberMe_Unselect_12X12.png.png") {
            img.setAttribute("src", "<%=request.getContextPath() %>/static/images/BTN_RemeberMe_Select_12x12.png.png");
        } else {
            img.setAttribute("src", "<%=request.getContextPath() %>/static/images/BTN_RemeberMe_Unselect_12X12.png.png");
        }
        }
    </script>
  </head>

  <body>
  <%
      String tip_message = (String)request.getAttribute(Oes.TIP_MESSAGE);
      String visibility = "hidden";
      if (tip_message != null && !tip_message.equals("")) {
          visibility = "visible";
      }
  %>
    <div class="login_title">augmentum</div>
    <div class="login_introduce">Online Exam Web System</div>
    <div class="login_welcome">Welcome to login!</div>
    <div class="login_form">
      <form action="login.action" method="POST" id="loginForm">
        <div class="login_form_errorMessage" id="errorMsg" style ="visibility:<%= visibility%>"> <%=tip_message %> </div>
        <div class="login_form_line" style="margin-top:60px">
          <input type="hidden" name="go" value=<%=request.getAttribute("go") %> />
          <input type="hidden" name="queryString" id="queryString"/>
          <img src="<%=request.getContextPath() %>/static/images/ICN_Usename_20x20.png.png"></img>
          <input type="text" placeholder="Username" name="userName" id="userName" />
          <div class="login_form_errorIcon" id="name_worng_img" style ="visibility:hidden" ></div><br/>
        </div>
        <div class="login_form_line">
          <img src="<%=request.getContextPath() %>/static/images/ICN_Password_20x15.png.png" style="width:15px"></img>
          <input type="password" placeholder="Password" name="password" id="password" />
        <div class="login_form_errorIcon" id="password_worng_img" style ="visibility:hidden"/></div><br/>
        </div>
        <div class="login_form_button" onclick="login()" >Login </div>
        <div class="login_form_other">
          <div ><img style="top:0px ; left:0px ;height: 12px;width: 12px; cursor: pointer;" class="remeber_img" src="<%=request.getContextPath() %>/static/images/BTN_RemeberMe_Unselect_12X12.png.png" id="img" onclick="change()"></div>
          <div class="login_form_other_remeber">Remeber me</div>
          <div class="login_form_other_forget">Forget password?</div>
        </div>
      </form>
    </div>
    <div class="login_footer">Copyright © 2017augmentum, Inc.All Rights Reserved</div>
<script>
var queryString = location.hash;
document.getElementById("queryString").value = queryString;
</script>
  </body>
</html>