<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<%
  request.setCharacterEncoding("UTF-8");
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>로그인창</title>
<c:choose>
	<c:when test="${result=='loginFailed'}">
		<script>
			window.onload=function(){
				alert("아이디나 비밀번호가 틀립니다. 다시 로그인 하세요!")
			}
		</script>
	</c:when>
</c:choose>
</head>
<body>
	<form method="post"   action="${contextPath}/member/login.do">
	<h1  class="text_center">로그인창</h1>
	<table  align="center">
	   <tr>
	      <td width="200"><p align="right">아이디</td>
	      <td width="400"><input type="text" name="id" value="${member.id}"></td>
	   </tr>
	   <tr>
	      <td width="200"><p align="right">비밀번호</td>
	      <td width="400"><input type="password" name="pwd" value="${member.pwd}"></td>
	    </tr>
	    <tr>
	       <td colspan="2"><input type="submit" value="로그인"><input type="reset" value="다시입력"></td>
	    </tr>
	</table>
	</form>
</body>
</html>