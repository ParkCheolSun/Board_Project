<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="article" value="${articleMap.article }" />
<c:set var="imageFileList" value="${articleMap.imageFileList }" />
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세내역</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function readURL(input) {
		if(input.files && input.files[0]){
			var reader = new FileReader();
			reader.onload = function(e) {
			$('#preview').attr('src',e.target.result);
			}
		reader.readAsDataURL(input.files[0]);
		}
	}
	function backToList(obj) {
		obj.action = "${contextPath}/board/listArticles.do";
		obj.submit();
	}
	function fn_enable(obj) {
		document.getElementById("i_title").disabled = false;
		document.getElementById("i_content").disabled = false;
		document.getElementById("i_imageFileName").disabled = false;
		document.getElementById("tr_btn_modify").style.disabled = "block";
		document.getElementById("tr_btn").style.disabled = "none";
	}
	function fn_modify_article(obj) {
		obj.action = "${contextPath}/board/modArticle.do";
		obj.submit();
	}
	function fn_remove_article(url, articleNO) {
		var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","articleNO");
	     articleNOInput.setAttribute("value", articleNO);
		 
	     form.appendChild(articleNOInput);
	     document.body.appendChild(form);
	     form.submit();
	}
	function fn_reply_form(isLogOn, url, parentNO) {
		if(isLogOn != '' && isLogOn != 'false'){
			var form = document.createElement("form");
			form.setAttribute("method","post");
			form.setAttribute("action", url);
			var articleNOInput = document.createElement("input");
	    	articleNOInput.setAttribute("type","hidden");
	     	articleNOInput.setAttribute("name","parentNO");
	     	articleNOInput.setAttribute("value", parentNO);
	     
	     	form.appendChild(articleNOInput);
	     	document.body.appendChild(form);
	     	form.submit();
		 }else{
		     alert("로그인 후 글쓰기가 가능합니다.");
		     location.href="${contextPath}/member/loginForm.do?action=/board/replyForm.do&parentNO="+parentNO;
		  }

	}
</script>
</head>
<body>
	<form name="frmArticle" method="post" enctype="multipart/form-data">
		<table border="0" align="center">
			<tr>
				<td width="20%" align="center" bgcolor="#ff9933">글번호</td>
				<td><input type="text" value="${article.articleNO }"
					disabled="disabled" /> <input type="hidden" name="articleNO"
					value="${article.articleNO }" /></td>
			</tr>
			<tr>
				<td width="20%" align="center" bgcolor="#ff9933">작성자 아이디</td>
				<td><input type="text" value="${article.id }" name="writer"
					disabled="disabled" /></td>
			</tr>
			<tr>
				<td width="20%" align="center" bgcolor="#ff9933">제목</td>
				<td><input type="text" value="${article.title }" name="title"
					id="i_title" disabled="disabled" /></td>
			</tr>
			<tr>
				<td width="20%" align="center" bgcolor="#ff9933">내용</td>
				<td><textarea rows="20" cols="60" id="i_content" name="content"
						disabled="disabled" />${article.content }</textarea></td>
			</tr>
			<c:if test="${not empty imageFileList && imageFileList!='null' }">
				<c:forEach var="item" items="${imageFileList}" varStatus="status">
					<tr>
						<td width="150" align="center" bgcolor="#FF9933" rowspan="2">
							이미지${status.count }</td>
						<td><input type="hidden" name="originalFileName"
							value="${item.imageFileName }" /> <img
							src="${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${item.imageFileName}"
							id="preview" /><br></td>
					</tr>
					<tr>
						<td><input type="file" name="imageFileName "
							id="i_imageFileName" disabled onchange="readURL(this);" /></td>
					</tr>
				</c:forEach>
			</c:if>

			<c:choose>
				<c:when
					test="${not empty article.imageFileName && article.imageFileName!='null' }">
					<tr>
						<td width="20%" align="center" bgcolor="#ff9933" rowspan="2">
							이미지</td>
						<td><input type="hidden" name="originalFileName"
							value="${article.imageFileName }" /> <img
							src="${contextPath }/download.do?imageFileName=${article.imageFileName}&articleNO=${article.articleNO}"
							id="preview" /> <br></td>
					</tr>
					<tr>
						<td><input type="file" name="imageFileName"
							id="i_imageFileName" disabled onchange="readURL(this);" /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr id="tr_file_upload">
						<td width="150" align="center" bgcolor="#FF9933" rowspan="2">이미지</td>
						<td><input type="hidden" name="orgnalFileName"
							value="${article.imageFileName }" /></td>
					</tr>
					<tr>
						<td></td>
						<td><img id="preview" /></br> <input type="file"
							name="imageFileName" id="i_imageFleName" disabled="disabled"
							onchange="readURL(this);" /></td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<td width="20%" align="center" bgcolor="#ff9933">등록일자</td>
				<td><input type="text"
					value='<fmt:formatDate value="${article.writeDate }"/>' disabled />
				</td>
			</tr>
			<tr id="tr_btn_modify">
				<td colspan="2" align="center"><input type="button"
					value="수정반영하기" onclick="fn_modify_article(frmArticle)" /> <input
					type="button" value="취소" onclick="backToList(frmArticle)">
				</td>
			</tr>
			<tr id="tr_btn">
				<td colspan="2" align="center"><c:if
						test="${member.id == article.id}">
						<input type="button" value="수정하기" onclick="fn_enable(this.form)" />
						<input type=button value="삭제하기"
							onClick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNO})">
					</c:if> <input type="button" value="리스트로 돌아가기"
					onclick="backToList(this.form)" /> <input type="button"
					value="답글쓰기"
					onclick="fn_reply_form('${isLogOn}', '${contextPath}/board/replyForm.do',${article.articleNO })">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>