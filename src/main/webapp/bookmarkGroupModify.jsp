<%@ page import="com.sc.mission1.BookmarkGroup" %>
<%@ page import="com.sc.mission1.Databases" %>
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
<h1>북마크 그룹 수정</h1>
<%@include file="header.jsp" %>
<%
    request.setCharacterEncoding("UTF-8");
    String message = "";
    BookmarkGroup bookmarkGroup = new BookmarkGroup();

    if (request.getMethod().equalsIgnoreCase("POST")) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int orderNo = Integer.parseInt(request.getParameter("order_no"));


        bookmarkGroup.setId(id);
        bookmarkGroup.setName(name);
        bookmarkGroup.setOrderNo(orderNo);

        Databases databases = new Databases();
        int result = databases.modifyBookmarkGroup(id, name, orderNo);

        if (result > 0) {
            message = "수정 성공!";
        } else if (result == -1) {
            message = "이미 같은 순서가 있습니다.";
        } else {
            message = "수정 실패";
        }
    } else {
%>
<form method="post" id="modify-form">
    <table>
        <tr>
            <th>북마크 이름</th>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <th>순서</th>
            <td><input type="text" name="order_no"></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <a href="bookmarkGroup.jsp">돌아가기</a>
                <a>|</a>
                <button type="submit">수정</button>
            </td>
        </tr>
    </table>
</form>
<%
    }
%>
<script>
    var message = "<%= message %>";
    if (message) {
        alert(message);
        location.href = "bookmarkGroup.jsp";
    }
</script>
</body>
</html>
