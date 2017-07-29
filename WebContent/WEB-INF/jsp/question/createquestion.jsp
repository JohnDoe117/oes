<%@page import="com.augmentum.oes.util.StringUtil"%>
<%@page import="com.augmentum.oes.model.Question"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Create question</title>
    <%Question question = (Question)request.getAttribute("question"); %>
  <link href="<%=request.getContextPath() %>/static/reset.css" rel="stylesheet" type="text/css" />
  <link href="<%=request.getContextPath() %>/static/common.css" rel="stylesheet" type="text/css" />
  <link href="<%=request.getContextPath() %>/static/save.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <div id="header">
      <img id="icon" src="<%=request.getContextPath() %>/static/images/LOGO_Web_40x240.png" />
      <div id="title">Online Exam Web System</div>
      <div id="user">
      <img src="<%=request.getContextPath() %>/static/images/ICN_Web_PersonalInformation_20x20.png  .png"/> 
      <span class="username">${sessionScope.USER.userName }</span> <span
      class="logout"><a href="${pageContext.request.contextPath}/logout">Logout</a></span>
      </div>
    </div>
    <div id="nav">
      <ul>
        <li class="active1 question" >Question Management</li>
        <li class="exam">Exam Management</li>
      </ul>
    </div>
    <div id="content">
      <div id="bread" style="background-color:#D2DAE3;">Question Management > Create Question</div>
      <div id="border">
        <div class="main">
          <form method="POST" action="createquestion.action" id="createqusetionForm">
            <input type="hidden" name="id" value="<%=StringUtil.doWithNull(question.getId()) %>">
            <div class="question">
              <div>Question:</div>
              <div><input type="text" name="question" id="question" 
              value="<%=StringUtil.doWithNull(question.getQuestion()) %>"></div>
              <div class="answer">
                <div>Answer:</div>
                  <div class="choose">
                    <input type="radio" name="answer" id = "option_a" value="option_a"> A <input type="text" 
                    name="option_a" id="option_a"  value="<%=StringUtil.doWithNull(question.getOptionA()) %>"/><br />
                    <input type="radio" name="answer" id = "option_b" value="option_b"> B <input type="text" 
                    name="option_b" id="option_b"  value="<%=StringUtil.doWithNull(question.getOptionB()) %>"/><br />
                    <input type="radio" name="answer" id = "option_c" value="option_c"> C <input type="text" 
                    name="option_c" id="option_c"  value="<%=StringUtil.doWithNull(question.getOptionC()) %>"/><br />
                    <input type="radio" name="answer" id = "option_d" value="option_d"> D <input type="text" 
                    name="option_d" id="option_d"  value="<%=StringUtil.doWithNull(question.getOptionD()) %>"/><br />
                </div>
              </div>
            <div class="button">
              <input type="button" value="save" onclick="save()" class="btn">
              <input type="button" value="cancel" onclick="cancel()" class="btn">
            </div>
         </form>
        </div>
      </div>
      <div class="footer" style="background-color:#D2DAE3;"> 
        Copyright @ 2017 Augmentum, Inc.All Rights Reserved. 
      </div>
    </div>

  <script>
      var answer = "<%=question.getAnswer() %>";
      console.log(answer);
      document.getElementById(answer).checked = true;
      function save() {
          var form = document.getElementById("createqusetionForm");
          form.submit();
     }
      function cancel() {
          var form = document.getElementById("createqusetionForm");
          form.reset();
     }
  </script>
</body>
</html>