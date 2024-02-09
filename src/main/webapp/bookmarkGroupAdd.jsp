<%@ page import="com.sc.mission1.dto.BookmarkGroup" %>
<%@ page import="com.sc.mission1.serivce.Databases" %>
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
<h1>북마크 그룹 추가</h1>
<%@include file="header.jsp" %>
<%
    request.setCharacterEncoding("UTF-8");
    String message = "";

    if (request.getMethod().equalsIgnoreCase("POST")) {
        String name = request.getParameter("name");
        int orderNo = Integer.parseInt(request.getParameter("order_no"));

        BookmarkGroup bookmarkGroup = new BookmarkGroup();
        bookmarkGroup.setName(name);
        bookmarkGroup.setOrderNo(orderNo);

        Databases databases = new Databases();
        int result = databases.addBookmarkGroup(bookmarkGroup);

        if (result > 0) {
            message = "추가 성공!";
        } else if (result == -1) {
            message = "이미 같은 순서가 있습니다.";
        } else {
            message = "추가 실패";
        }
    } else {
%>
<form method="post" id="add-form">
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
                <input type="submit" value="추가">
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
