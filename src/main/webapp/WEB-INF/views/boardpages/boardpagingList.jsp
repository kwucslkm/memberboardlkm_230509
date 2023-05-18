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
</head>
<body>
<%@include file="../component/header.jsp" %>
<%@include file="../component/nav.jsp" %>
<div id="section">
    <h2>글목록</h2>
    <div class="container" id="search-area">
        <form action="/pagingList" method="get">
            <select name="type" id="selectOpt">
                <option value="boardTitle">제목</option>
                <option value="boardWriter">작성자</option>
            </select>
            <input type="text" id="searchValue" name="q" placeholder="검색어를 입력하세요">
            <input type="submit" value="검색" onclick="searchValueSave()">
        </form>
    </div>
    <div id="boardListL">
        <div id="listTableLevel">
            <select name="pageMaxBoard" id="selectPageMaxBoard" onchange="Page_maxBoard(this.value)">
                <option value="">보기선택</option>
                <option value="3">3개씩보기</option>
                <option value="5">5개씩보기</option>
                <option value="7">7개씩보기</option>
                <option value="10">10개씩보기</option>
            </select>
        </div><br>
        <table id="boardListTable">
            <tr>
                <th>글번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>본문내용</th>
                <th>작성시간</th>
                <th>조회수</th>
                <th>첨부파일</th>

            </tr>
            <c:forEach items="${pagingList}" var="board">
                <tr>
                    <td>${board.id}</td>
                    <td id="tdSize">
                        <a href="/board?id=${board.id}&q=${q}&type=${type}&page=${paging.page}&pageMaxBoard=${pageMaxBoard}">${board.boardTitle}</a>
                        <c:if test="${board.fileAttached == 1}">
                            <%--                        <span style="color: #0a53be; font-size: 12px">--%>
                            <span>
                            @
                        </span>
                        </c:if>
                        <c:forEach items="${commentCnt}" var="bdCommentCnt">
                            <c:if test="${board.id == bdCommentCnt.boardId}">
                                <%--                    <span style="color: #0a53be; font-size: 12px">--%>
                                <span>
                            (${bdCommentCnt.boardCommentCount})
                    </span>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${board.boardWriter}</td>
                    <td>${board.boardContents}</td>
                    <td><fmt:formatDate value="${board.boardCreatedDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                    <td>${board.boardHits}</td>
                    <td>${board.fileAttached}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
    <div class="container">
        <ul class="pagination justify-content-center">
            <c:choose>
                <%-- 현재 페이지가 1페이지면 [이전] 글자만 보여줌 --%>
                <c:when test="${paging.page<=1}">
                    <li class="page-item disabled">
                        <a class="page-link">[이전]</a>
                    </li>
                </c:when>
                <%-- 1페이지가 아닌 경우에는 [이전]을 클릭하면 현재 페이지보다 1 작은 페이지 요청 --%>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link"
                           href="/pagingList?page=${paging.page-1}&q=${q}&type=${type}&pageMaxBoard=${pageMaxBoard}">[이전]</a>
                            <%--                    <a class="page-link" href="/board/pagingList?page=${paging.page-1}&q=${q}&type=${type}">[이전]</a>--%>
                    </li>
                </c:otherwise>
            </c:choose>
            <%--        페이지 표현부--%>
            <%--  for(int i=startPage; i<=endPage; i++)      --%>
            <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="i" step="1">
                <c:choose>
                    <%-- 요청한 페이지에 있는 경우 현재 페이지 번호는 텍스트만 보이게 --%>
                    <c:when test="${i eq paging.page}">
                        <li class="page-item active">
                            <a class="page-link">${i}</a>
                        </li>
                    </c:when>

                    <c:otherwise>
                        <li class="page-item">
                                <%--                    <a class="page-link" href="/board/paging?page=${i}&q=${q}&type=${type}">${i}</a>--%>
                            <a class="page-link"
                               href="/pagingList?page=${i}&q=${q}&type=${type}&pageMaxBoard=${pageMaxBoard}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose>
                <c:when test="${paging.page>=paging.maxPage}">
                    <li class="page-item disabled">
                        <a class="page-link">[다음]</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link"
                           href="/pagingList?page=${paging.page+1}&q=${q}&type=${type}&pageMaxBoard=${pageMaxBoard}">[다음]</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>

    </div>
</div>

<%@include file="../component/footer.jsp" %>

</body>
<script>
    const Page_maxBoard = (pages) => {
        console.log(pages + "개씩만 봅니다.");
        location.href = "/pagingList?q=${q}&type=${type}&page=${paging.page}&pageMaxBoard=" + pages;

    }
</script>
</html>