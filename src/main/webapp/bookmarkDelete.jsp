<%@ page import="com.sc.mission1.serivce.Databases" %>
<%@ page import="com.sc.mission1.dto.Bookmark" %>
<%@ page import="com.sc.mission1.dto.BookmarkGroup" %>
<%@ page import="com.sc.mission1.dto.Wifi" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        th {
            width: 10%;
        }
    </style>
</head>
<body>
<h1>북마크 삭제</h1>
<%@include file="header.jsp" %>
<%
    request.setCharacterEncoding("UTF-8");
    String message = "";
    String id = request.getParameter("id");
    Databases databases = new Databases();
    Bookmark bookmark = databases.selectBookmark(Integer.parseInt(id));
    BookmarkGroup bookmarkGroup = databases.selectBookmarkGroup(bookmark.getBookmarkGroupId());
    Wifi wifi = databases.wifiDetail(bookmark.getMgrNo());

    if (request.getMethod().equalsIgnoreCase("POST")) {
        int result = databases.deleteBookmark(Integer.parseInt(id));

        if (result > 0) {
            message = "삭제 성공!";
        } else {
            message = "삭제 실패";
        }
    } else {
%>
<div>
    북마크를 삭제하시겠습니까?
</div>
<form method="post" id="modify-form">
    <div style="margin-top: 20px">
        <table>
            <tr>
                <th>북마크 이름</th>
                <td><%=bookmarkGroup.getName()%>
                </td>
            </tr>
            <tr>
                <th>와이파이명</th>
                <td><%=wifi.getXSwifiMainNm()%>
                </td>
            </tr>
            <tr>
                <th>등록일자</th>
                <td><%=bookmark.getRegDttm()%>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <a href="bookmark.jsp">돌아가기</a>
                    <a>|</a>
                    <button type="submit">삭제</button>
                </td>
            </tr>
        </table>
    </div>
</form>
<%
    }
%>
<script>
    var message = "<%= message %>";
    if (message) {
        alert(message);
        location.href = "bookmark.jsp";
    }
</script>
</body>
</html>
