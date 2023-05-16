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
    <h2>회원정보수정</h2><br>
    <form action="/memberUpdate" method="post" id="memberUpdateForm" name="member_Update_form" enctype="multipart/form-data">
        <input type="text" name="id" id="memberId" readonly value="${member.id}"><br>
        <input type="text" name="memberEmail" class="memberUpdateInput" id="member-email" value="${member.memberEmail}">
        <input type="text" name="memberName" class="memberUpdateInput" value="${member.memberName}"> <br>
        <input type="text" name="memberMobile" class="memberUpdateInput" value="${member.memberMobile}" > <br>
        <input type="file" name="memberProfileFile" multiple> <br>
        <input type="text" name="memberPassword" id="memberPassword" placeholder="비밀번호"> <br>
        <input type="button" value="수정" onclick="member_Update(${member.memberPassword})">
    </form>
</div>


<%@include file="../component/footer.jsp" %>
</body>
<script>
    const member_Update = (password) => {
        const memberPassword = document.getElementById("memberPassword");
        console.log(memberPassword.value);
        console.log(password);
        if (memberPassword.value == password){
            document.member_Update_form.submit();

        }else {
            alert("비밀번호가 일치 하지 않습니다.!!")
        }


    }

    // const memberSaveResult = () => {
    //     alert("회원가입 성공")
    //
    // }
</script>
</html>