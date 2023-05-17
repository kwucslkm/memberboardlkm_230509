<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-09
  Time: 오후 3:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
<%@include file="../component/header.jsp" %>
<%@include file="../component/nav.jsp" %>

<div id="section">
    <h2>글 쓰 기</h2>
    <form action="/boardSave" method="post" enctype="multipart/form-data">
        작성자:<input type="text" name="boardWriter" id="Writer" readonly > <br>
        제목:<input type="text" name="boardTitle" placeholder="제목을 입력하세요"> <br>
        내용:<textarea name="boardContents" cols=30" rows="40"></textarea> <br>
        <input type="file" name="boardFile" multiple> <br>
        <input type="submit" value="작성">
    </form>
</div>
<%@include file="../component/footer.jsp" %>
</body>
<script>
    console.log("${member.memberName}");
    document.getElementById("Writer").value ="${member.memberName}";

</script>
</html>