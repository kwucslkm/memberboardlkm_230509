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
    <h2>회원가입</h2><br>
    <form action="/saveMember" method="post" id="memberSaveForm" enctype="multipart/form-data">
        <input type="text" name="memberEmail" placeholder="이메일" class="memberSaveInput" id="member-email"
               onblur="emailchk()">
        <h6 id="check-result"></h6><br>
        <input type="text" name="memberPassword" class="memberSaveInput" id="password" onblur="password_chk()" placeholder="비밀번호">
        <h6 id="pwCheck-result"></h6><br>
        <input type="text" name="memberName" class="memberSaveInput" placeholder="이름"> <br>
        <input type="text" name="memberMobile" class="memberSaveInput" id="memberPhone" onblur="memberPhone_chk()" placeholder="전화번호">
        <h6 id="phone-result"></h6>

        <input type="file" name="memberProfileFile" class="memberSaveInput" multiple> <br>
        <input type="submit" class="memberSaveInput" value="회원가입" onclick="memberSaveResult()">

    </form>
</div>


<%@include file="../component/footer.jsp" %>
</body>
<script>
    const tel1 = /^\d{3}-\d{4}-\d{4}$/;

    const memberPhone_chk = () => {
        const memberPhone = document.getElementById("memberPhone");
        const phoneChkResult = document.getElementById("phone-result");
        if(memberPhone.length == 0){

            phoneChkResult.innerText = "필수 입력 사항입니다.";
            phoneChkResult.style.color="red";
        }else if(!memberPhone.value.match(tel1)) {
            phoneChkResult.innerText="000-0000-0000으로 입력 해 주세요";
            phoneChkResult.style.color="red";
            memberPhone.focus();

        }else{
            phoneChkResult.innerText = "좋아요";
            phoneChkResult.style.color="green";

        }

    }
    const emailchk = () => {
        const email = document.getElementById("member-email").value;
        const emailChkResult = document.getElementById("check-result");
        $.ajax({
            type: "post",
            url: "/email-chk",
            data: {
                "memberEmail": email
            },
            success: function () {

                emailChkResult.innerHTML = "멋진 이메일입니다.";
                emailChkResult.style.color = "green";
            },
            error: function (err) {
                //성공이 아닌경우 응답을 err로 받음.
                //err내부의 status에는 서버에서 응답한 http status code 값이 담겨 있음.
                //status code 값으로 화면에 출력하는 부분 제어
                if (err.status == "409") {
                    emailChkResult.innerHTML = "중복 이메일입니다.";
                    emailChkResult.style.color = "red";

                } else if (err.status == "404") {
                    emailChkResult.innerHTML = "필수 입력 입니다.";
                    emailChkResult.style.color = "red";
                }
            }
        })
    }
    const password_chk = () => {
        const passwordChk=document.getElementById("password");
        const passwordChkResult = document.getElementById("pwCheck-result");
        const exp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!#$])[A-Za-z\d!#$]{8,16}$/;
        console.log("password chk go~~")
        if(passwordChk.value.match(exp)){
            passwordChkResult.innerHTML = "사용가능합니다.";
            passwordChkResult.style.color="green";

        }else {
            passwordChkResult.innerHTML="올바른 형식이 아닙니다.(8~16자)영문대소문자,숫자,특수기호(!#$)필수"
            passwordChkResult.style.color="red"
            passwordChk.focus();
        }
    }
    // const memberSaveResult = () => {
    //     alert("회원가입 성공")
    //
    // }
</script>
</html>