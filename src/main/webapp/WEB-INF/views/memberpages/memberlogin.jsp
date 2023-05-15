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
  <h2>로그인</h2>
  <form action="/memberLogin"  id="memberLoginForm" method="post" >
    <input type="text" name="memberEmail" class="memberLoginInput" placeholder="이메일을 입력하세요"  id="member-email">
    <p id="check-result"></p>
    <input type="text" name="memberPassword" class="memberLoginInput" placeholder="비밀번호를 입력하세요"> <br>
    <h6 id="loginchk"></h6>
    <input type="submit" class="memberLoginInput" value="로그인" onclick="loginchk()">
  </form>
</div>


<%@include file="../component/footer.jsp" %>
</body>
<script>
const loginchk = () => {

  // alert("로그인되었습니다.")


}




</script>
</html>