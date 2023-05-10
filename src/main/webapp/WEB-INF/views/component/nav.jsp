<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="nav">
    <ul>
        <li>
            <a href="/">
                <i class="bi bi-house"></i>
            </a>
        </li>
        <li id="navMenuSave">
            <a href="/save_member">회원가입</a>
        </li>
        <li id = "navMenulogin">
            <a href="/memberLogin">로그인</a>
        </li>
        <li id="navMenuList">
            <a href="/findAll">목록</a>
        </li>
        <li id="navMenus1">

        </li>
        <li class="login-name" id="login-area">

        </li>
    </ul>
</div>

<script>
    const loginArea = document.getElementById("login-area");
    const loginmenu1 = document.getElementById("navMenuSave");
    const loginmenu2 = document.getElementById("navMenulogin")
    const loginEmail = '${sessionScope.loginEmail}';
    console.log(loginEmail.length);

    if (loginEmail.length != 0) {
        loginArea.innerHTML = "<a href='/mypage' style='color: yellow;'>"+loginEmail +"마이페이지!</a>"+
                                "<a href='/logout'>logout</a>";
        loginmenu1.innerHTML = "<a href = '/boardSave'>게시글쓰기</a>";
        loginmenu2.innerHTML = "<a href = '/boardFindByEmail'>내글보러가기</a>";
    } else {
        loginArea.innerHTML = "<a href='/memberLogin'>login</a>";
    }
</script>