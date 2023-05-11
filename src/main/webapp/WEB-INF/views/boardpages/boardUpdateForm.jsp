<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-09
  Time: 오후 3:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>

</head>
<body>
<%@include file="../component/header.jsp" %>
<%@include file="../component/nav.jsp" %>

<div id="section">
    <h2>글 수정</h2><br>
    <form action="/boardUpdate" method="post" id="boardUpdateForm" name="boardUpdateForm" enctype="multipart/form-data">

       게시글번호: <input type="text" name="id" id="boardId" value="${boardUpdate.id}" readonly > <br>
        작성자:<input type="text" name="boardWriter" value="${boardUpdate.boardWriter}" readonly disabled> <br>
        글제목:<input type="text" name="boardTitle" value="${boardUpdate.boardTitle}"> <br>
        글내용:<textarea name="boardContents" cols="50" rows="50">${boardUpdate.boardContents}</textarea> <br>
        <input type="text" name="memberPassword" id="memberPassword" placeholder="비밀번호"> <br>
        <input type="button" value="수정" onclick="boardUpdate(${bmember.memberPassword})">

    </form>
</div>


<%@include file="../component/footer.jsp" %>
</body>
<script>
    const boardUpdate = (memberPassword) => {
        const password = document.getElementById("memberPassword").value;
        const boardId = document.getElementById("boardId").value;
        console.log(boardId);
        console.log(password);
        console.log(memberPassword);
        if (memberPassword == password) {
            document.boardUpdateForm.submit();
        } else {
            alert("비밀번호가 일치 하지 않습니다.!!")
        }
    }
</script>
</html>