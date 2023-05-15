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
    <h2>글목록</h2>
    <table>
        <tr>
            <th>글번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>본문내용</th>
            <th>작성시간</th>
            <th>조회수</th>
            <th>첨부파일</th>

        </tr>
        <c:forEach items="${boardList}" var="board">
            <tr>
                <td>${board.id}</td>
                <td><a href="/board?id=${board.id}">${board.boardTitle}</a>
                    <c:if test="${board.fileAttached == 1}">
                        @
                    </c:if>

                </td>
                <td>${board.boardWriter}</td>
                <td>${board.boardContents}</td>
                <td>${board.boardCreatedDate}</td>
                <td>${board.boardHits}</td>
                <td>${board.fileAttached}</td>

            </tr>
        </c:forEach>
    </table>

</div>

<%@include file="../component/footer.jsp" %>

</body>
<script>

</script>
</html>