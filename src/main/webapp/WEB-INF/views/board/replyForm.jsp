<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function readURL(input, index) {
		if(input.files && input.files[0]){
			var reader = new FileReader();
			reader.onload = function(e) {
			$('#preview'+index).attr('src',e.target.result);
			}
		reader.readAsDataURL(input.files[0]);
		}
	}
	var cnt=1;
	function fn_addFile() {
		$("#d_file").append('<tr bordercolor="#ff0000" align="center" width=100%  align=center>');
		$("#d_file").append("<td><input type='file' name='file"+cnt+"' onchange='readURL(this,"+ cnt + ")' /></td>");
		$("#d_file").append("<td>"+"<img  id='preview" + cnt + "' width=150 height=150/></td></tr>");
		cnt++;
	}
	function backToList(obj) {
		obj.action = "${contextPath}/board/listArticles.do";
		obj.submit();
	}
</script>
</head>
<body>
	<h1 style="text-align: center;">답글 쓰기</h1>
	<form name="frmReply" method="post" action="${contextPath }/board/addNewArticle.do" enctype="multipart/form-data">
		<table border="1" align="center">
			<tr>
				<td align="center" bgcolor="#ff9933">작성자</td>
				<td><input type="text" size="67" maxlength="100" name="name" value="${name}" readonly="readonly"/> </td>
			</tr>
			<tr>
				<td align="center" bgcolor="#ff9933">제목</td>
				<td><input type="text" size="67" maxlength="100" name="title"/> </td>
			</tr>
			<tr>
				<td align="center" valign="middle" bgcolor="#ff9933">글내용</td>
				<td><textarea rows="10" cols="65" name="content" maxlength="4000" border="0" resize="none"></textarea></td>
			</tr>
			<tr>
				<td align="center" bgcolor="#ff9933">
				이미지 파일 첨부<br>
				<input type="button" value="파일추가" onclick="fn_addFile()"/> 
				</td>
				<td><div id="d_file"></div></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="답글반영하기"/>
					<input type="button" value="취소" onclick="backToList(this.form)"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>