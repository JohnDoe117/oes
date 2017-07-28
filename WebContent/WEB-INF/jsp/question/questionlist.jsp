<%@page import="com.augmentum.oes.model.Pagination"%>
<%@page import="com.augmentum.oes.model.Question"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.augmentum.oes.Oes"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DecimalFormat"%>
<!DOCTYPE html>
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>questionlist</title>
  <link href="<%=request.getContextPath() %>/static/reset.css" rel="stylesheet" type="text/css" />
  <link href="<%=request.getContextPath() %>/static/common.css" rel="stylesheet" type="text/css" />
  <link href="<%=request.getContextPath() %>/static/list.css" rel="stylesheet" type="text/css" />
    
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
    <div class="system_outer_boder">
      <div class="question_boder">
        <div class="question_list_left_button">
          <div>Question List</div>
          <div>    
            <form action="editquestion.action" method="GET" id="addqusetion_form">
            <input type="button" value="createquestion" class="button" onclick="addquestion()" >
           </form>
         </div>
        </div>
        <div class="question_list_right">
          <div class="question_list_column_title">
            <div class="question_list_column_title_1">&nbsp;</div>
            <div class="question_list_column_title_2">
            <form action="questionlist.action" method="GET" id="order_form">
            <input type="hidden" name="orderBy" id="orderBy" value="desc"/>
            <input type="submit" value="ID" class="button" />
            </form>
            </div>
            <div class="question_list_column_title_3">Description</div>
            <div class="question_list_column_title_5">Delete</div>
            <div class="question_list_column_title_4">Edit</div>
          </div>
          <div class="question_list_row">
          <form method="GET" action="deletequestion.action" id="createqusetionForm">
          <%
          int i = 0;
          List<Question> questions = (List<Question>)request.getAttribute("questions");
          for(Question question : questions) {
              i++;
          %>
         
            <ul class="question_list_row_once_line" style="list-style:none;">
              <li class="question_list_row_once_line_1"><%=i %></li>
              <li class="question_list_row_once_line_2">
                Q<%
                int id = question.getId();
                String formatId = new DecimalFormat("0000").format(id);
                out.print(formatId);
                %></li>
              <li class="question_list_row_once_line_3"><%=question.getDesciption() %></li>
              <li class="question_list_row_once_line_5"><input type="checkbox" name="choseDelete" value="<%=question.getId() %>"/></li>
              <li class="question_list_row_once_line_4"><a href="<%=request.getContextPath() %>/editquestion.action?id=<%=question.getId() %>">Edit</a>
            </ul>
           <%
          }
          %>
          <input type="submit" value="delete">
          </form>
          </div>
          <%Pagination pagination = (Pagination)request.getAttribute("pagination"); %>

          <div class="question_list_delete_button">
            <div class="digg"> 
              <a href="<%=request.getContextPath()%>/questionlist.action?currentPage=<%=pagination.getCurrentPage() - 1%>">&lt;&nbsp;&nbsp; </a>
              <input type="text" id="currentPage" class="pagination_input" name="currentPage" value="<%=pagination.getCurrentPage() %>"/>
              <a href="<%=request.getContextPath()%>/questionlist.action?currentPage=<%=pagination.getCurrentPage() + 1%>"> &nbsp;&nbsp;&gt; </a>
            </div> 
          </div>
        </div>
      </div>
    </div>
    <script>
        function addquestion() {
            var AddquestionObj=document.getElementById("addqusetion_form");
            AddquestionObj.submit();
        }

    </script>
  </body>
</html>