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
    <h2>회원목록</h2>
    <table>
        <tr>
            <th>회원번호</th>
            <th>이메일</th>
            <th>비밀번호</th>
            <th>이름</th>
            <th>모바일</th>
            <th>프로필</th>

        </tr>
        <c:forEach items="${memberList}" var="member">
            <tr>
                <td>
                    <a href="/member?id=${member.id}">${member.id}</a>
                </td>
                <td>
                    <a href="/memberPage?id=${member.id}">${member.memberEmail}</a>
                    <c:if test="${member.memberProfile == 1}">
                        @
                    </c:if>
                </td>
                <td>${member.memberPassword}</td>
                <td>${member.memberName}</td>
                <td>${member.memberMobile}</td>
                <td>${member.memberProfile}</td>

            </tr>
        </c:forEach>
    </table>

</div>

<%@include file="../component/footer.jsp" %>

</body>
<script>

</script>
</html>