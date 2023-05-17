<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-05-09
  Time: 오후 3:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<h2>회원상세정보</h2>
<div id="section">
    <h2>회원상세정보</h2>

    <table>
        <tr>
            <th>id</th>
            <td>${member.id}</td>
        </tr>
        <tr>
            <th>email</th>
            <td>${member.memberEmail}</td>
        </tr>
        <tr>
            <th>password</th>
            <td>${member.memberPassword}</td>
        </tr>
        <tr>
            <th>name</th>
            <td>${member.memberName}</td>
        </tr>
        <tr>
            <th>mobile</th>
            <td>${member.memberMobile}</td>
        </tr>
        <tr>
            <th>memberprofile</th>
            <td>${member.memberProfile}</td>
        </tr>
        <c:if test="${member.memberProfile == 1}">
            <tr>
                <th>memberprofileJPG</th>
                <td>
                    <c:forEach items="${memberPofile}" var="memberFile">
                        <img src="${pageContext.request.contextPath}/upload/${memberFile.profileStoredFileName}"
                             alt="" width="100" height="100">

                    </c:forEach>
                </td>
            </tr>
        </c:if>
    </table>
    <c:if test="${sessionScope.loginEmail == member.memberEmail}">
        <button id="memberUpdate" onclick="memberUpdate()"> 내정보수정</button>
        <button id="memberOut" onclick="member_SelfOut()"> 회원탈퇴</button>
    </c:if>
    <br>
    <div id="memberDel">
        <h4 id="memberDelete"></h4>
        <button id="memberDelBtn" style="display: none; width: 20px; height: 10px"
                onclick="memberDel(${member.id})"></button>
    </div>
</div>
<%@include file="../component/footer.jsp" %>
</body>
<script>
    const memberDelLink = document.getElementById("memberDelete");
    const memberDelBtn = document.getElementById("memberDelBtn");
    const memberId = '${member.id}';
    const member_SelfOut = () => {
        location.href="/memberSelfOut?id="+memberId;
    }
    const memberUpdate = () => {
        location.href = "/memberUpdate?id=" +${member.id};
    }
    if ('${sessionScope.loginEmail}' == 'admin') {
        console.log("memberId " + memberId);
        // let memberDElHtml = "<a href=\'/memberDelete?id=";
        // memberDElHtml += memberId;
        // memberDElHtml += "\'>회원삭제</a>"
        // console.log(memberDElHtml);
        // memberDelLink.innerHTML = memberDElHtml;
        // 로그인 이메일이 'admin 관리자'일 경우 삭제 버튼을 보이게 한다.
        memberDelBtn.style.display = 'block';
        memberDelBtn.style.width = '100px';
        memberDelBtn.style.height = '30px';
        memberDelBtn.innerText = '회원삭제';
<%--
        memberDelLink.innerHTML = "<a href=\'/memberDelete?id=" + memberId + "\'>회원삭제</a>";
        --%>

    }
    const memberDel = (memberId) => {
        location.href = "/memberDelete?id=" + memberId;

    }
</script>
</html>