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

<div id="section">
    <h2>회원게시판</h2>
    <table>
        <tr>
            <th>게시글번호</th>
            <td>${boardDetail.id}</td>
        </tr>
        <tr>
            <th>boardWriter</th>
            <td>${boardDetail.boardWriter}</td>
        </tr>
        <tr>
            <th>boardTitle</th>
            <td>${boardDetail.boardTitle}</td>
        </tr>
        <tr>
            <th>boardContents</th>
            <td>${boardDetail.boardContents}</td>
        </tr>
        <tr>
            <th>boardCreatedDate</th>
<%--            <td>${boardDetail.boardCreatedDate}</td>--%>
            <td><fmt:formatDate value="${boardDetail.boardCreatedDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
        </tr>
        <tr>
            <th>boardHits</th>
            <td>${boardDetail.boardHits}</td>
        </tr>
        <c:if test="${boardDetail.fileAttached == 1}">
            <tr>
                <th>첨부파일</th>
                <td>
                    <c:forEach items="${boardFileList}" var="boardFile">
                        <img src="${pageContext.request.contextPath}/upload/${boardFile.boardStoredFileName}"
                             alt="" width="100" height="100">
                    </c:forEach>
                </td>
            </tr>
        </c:if>

    </table>
    <c:if test="${member.memberEmail == sessionScope.loginEmail}">
        <button onclick="board_update()">수정</button>
        <button onclick="board_delete()">삭제</button>
    </c:if>
    <br>
    <a href="/pagingList?page=${page}&q=${q}&type=${type}&pageMaxBoard=${pageMaxBoard}">목록</a><br><br>
    <div id="comment-writer-area">
        <input type="text" id="comment-writer" value="${sessionScope.loginEmail}" readonly>
        <input type="text" id="comment-contents" placeholder="내용">
        <button onclick="comment_write('${sessionScope.loginEmail}')">댓글작성</button>
    </div>
    <div id="comment-list">
        <c:choose>
            <c:when test="${commentList == []}">
                <h2>작성된 댓글이 없습니다.</h2>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>id</th>
                        <th>댓글작성자</th>
                        <th>댓글내용</th>
                        <th>작성시간</th>
                    </tr>
                    <c:forEach items="${commentList}" var="comment">
                        <tr>
                            <td>${comment.id}</td>
                            <td>${comment.commentWriter}</td>
                            <td>${comment.commentContents}</td>
                            <td>${comment.commentCreatedDate}</td>
                            <c:if test="${comment.memberId == loginMemberId}">
                                <td>
                                    <button id="commentDelBtn" class="commentDelbtn"
                                            style="height: 25px; width: 100px; background-color: seagreen; color: white;"
                                            onClick="commentDel(${comment.id})">댓글삭제
                                    </button>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@include file="../component/footer.jsp" %>
</body>
<script>
    const boardId = ${boardDetail.id};
    const board_update = () => {
        location.href = "/boardUpdate?id=" + ${boardDetail.id};
    }
    const board_delete = () => {
        location.href = "/boardDelete?id=" + ${boardDetail.id};

    }

    const comment_write = (commentWriteName) => {
        const commentContents = document.getElementById("comment-contents").value;
        <%--const boardId = ${boardDetail.id};--%>
        const result = document.getElementById("comment-list");
        <%--const memberemail = ${member.memberEmail};--%>
        // const commentdel = document.getElementById("commentDelBtn");
        // console.log(boardId);
        // console.log(commentWriteName);
        // console.log(commentContents);

        $.ajax({
            type: "post",
            url: "/commentSave",
            data: {
                "commentWriter": commentWriteName,
                "commentContents": commentContents,
                "boardId": boardId,
                "memberId": ${loginMemberId}
            },
            success: function (res) {
                // let commentCnt = 0;

                let output = "<table>";
                output += "<tr>";
                output += "<th>id</th>";
                output += "<th>작성자</th>";
                output += "<th>내용</th>";
                output += "<th>작성시간</th>";
                output += "</tr>";
                for (let i in res) {
                    console.log("댓글에서 찾아온 memberID="+res[i].memberId);
                    console.log("세션에서 찾아온 memberID="+${loginMemberId});
                    output += "<tr>"
                    output += "<td>" + res[i].id + "</td>";
                    output += "<td>" + res[i].commentWriter + "</td>";
                    output += "<td>" + res[i].commentContents + "</td>";
                    output += "<td>" + res[i].commentCreatedDate + "</td>";
                    if (res[i].memberId ==${loginMemberId}) {
                        // let commentid = res[i].id;
                        output += "<td>" + "<button id='commentDelBtn' class='commentDelbtn' " +
                            "style='height: 25px; width: 100px; background-color: seagreen;  color: white;' " +
                            "onClick='commentDel("+ res[i].id + ")'>댓글삭제"
                            + "</button>" + "</td>";
                    }
                    output += "</tr>";
                    <%--<button id="memberDelBtn" style="display: none; width: 20px; height: 10px" onclick="memberDel(${member.id})"></button>--%>
                    // output += "<td>" + res[i].commentCreatedDate+"<"
                    // output += "<td>" + moment(res[i].commentCreatedDate).format("YYYY-MM-DD HH:mm:ss") + "</td>";
                }
                output += "</table>";
                result.innerHTML = output;
                // document.getElementById("comment-writer").value = "";
                document.getElementById("comment-contents").value = "";

            },
            error: function () {
                console.log("실패");
            }

        });

    }
    const commentDel = (commentId) => {
        const result = document.getElementById("comment-list");
        // location.href = "/commentDel?id=" + commentId;
        $.ajax({
            type: "post",
            url: "/commentDel",
            data: {
                "id": commentId,
                "boardId": boardId
            },
            success: function (res) {

                let output = "<table>";
                output += "<tr>";
                output += "<th>id</th>";
                output += "<th>작성자</th>";
                output += "<th>내용</th>";
                output += "<th>작성시간</th>";
                output += "</tr>";
                for (let i in res) {

                    output += "<tr>"
                    output += "<td>" + res[i].id + "</td>";
                    output += "<td>" + res[i].commentWriter + "</td>";
                    output += "<td>" + res[i].commentContents + "</td>";
                    output += "<td>" + res[i].commentCreatedDate + "</td>";
                    if (res[i].memberId ==${loginMemberId}) {
                        output += "<td>" + "<button id='commentDelBtn' class='commentDelbtn' " +
                            "style='height: 25px; width: 100px; background-color: seagreen;  color: white;' " +
                            "onClick='commentDel("+ res[i].id + ")'>댓글삭제"
                            + "</button>" + "</td>";
                    }
                    output += "</tr>";
                    <%--<button id="memberDelBtn" style="display: none; width: 20px; height: 10px" onclick="memberDel(${member.id})"></button>--%>
                    // output += "<td>" + res[i].commentCreatedDate+"<"
                    // output += "<td>" + moment(res[i].commentCreatedDate).format("YYYY-MM-DD HH:mm:ss") + "</td>";
                }
                output += "</table>";
                result.innerHTML = output;
                // document.getElementById("comment-writer").value = "";
                document.getElementById("comment-contents").value = "";

            },
            error: function () {
                console.log("실패");
            }

        });


    }


</script>
</html>