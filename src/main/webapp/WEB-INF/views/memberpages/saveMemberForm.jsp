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
    <form action="/saveMember" method="post" enctype="multipart/form-data">
        <input type="text" name="memberEmail" placeholder="이메일" id="member-email" onblur="emailchk()"> <br>
        <p id="check-result"></p>
        <input type="text" name="memberPassword" placeholder="비밀번호"> <br>
        <input type="text" name="memberName" placeholder="이름"> <br>
        <input type="text" name="memberMobile" placeholder="전화번호"> <br>
        <input type="file" name="memberProfileFile" multiple> <br>
        <input type="submit" value="회원가입">
    </form>
</div>




<%@include file="../component/footer.jsp" %>
</body>
<script>
const emailchk = () => {
    const email = document.getElementById("member-email").value;
    const emailChkResult = document.getElementById("check-result");
    $.ajax({
        type : "post",
        url : "/email-chk",
        data : {
            "memberEmail" : email
        },
        success:function (){
            emailChkResult.innerHTML = "멋진 이메일입니다.";
            emailChkResult.style.color="green";
        },
        error : function (){
            emailChkResult.innerHTML = "중복 이메일입니다.";
            emailChkResult.style.color="red";
        }
    })



}
</script>
</html>