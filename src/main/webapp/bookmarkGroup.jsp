<%@ page import="com.sc.mission1.serivce.Databases" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sc.mission1.dto.BookmarkGroup" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<h1>북마크 그룹</h1>
<%@include file="header.jsp" %>
<button onclick="location.href='bookmarkGroupAdd.jsp'">북마크 그룹 이름 추가</button>
<%
    Databases databases = new Databases();

    String id = request.getParameter("id");
    if (id != null) {
        databases.deleteBookmarkGroup(Integer.parseInt(id));
    }
%>
<div>
    <table>
        <tr>
            <th>ID</th>
            <th>북마크 이름</th>
            <th>순서</th>
            <th>등록일자</th>
            <th>수정일자</th>
            <th>비고</th>
        </tr>
        <%
            List<BookmarkGroup> bookmarkGroupList = databases.bookmarkGroupList();
            if (bookmarkGroupList.isEmpty()) {
        %>
        <tr>
            <td colspan="6" style="text-align: center; height: 50px;">데이터가 존재하지 않습니다.</td>
        </tr>
        <%
        } else {
            for (BookmarkGroup bookmarkGroup : bookmarkGroupList) {
                String modify = bookmarkGroup.getModifyDttm() == null ? "" : String.valueOf(bookmarkGroup.getModifyDttm());
        %>
        <tr>
            <td><%=bookmarkGroup.getId()%>
            </td>
            <td><%=bookmarkGroup.getName()%>
            </td>
            <td><%=bookmarkGroup.getOrderNo()%>
            </td>
            <td><%=bookmarkGroup.getRegDttm()%>
            </td>
            <td><%=modify%>
            </td>
            <td style="text-align: center;">
                <a href="bookmarkGroupModify.jsp?id=<%=bookmarkGroup.getId()%>">수정</a>
                <button onclick="deleteBookmarkGroupList(<%=bookmarkGroup.getId()%>)">삭제</button>
            </td>
        </tr>
        <% }
        } %>
    </table>
</div>
<script>
    function deleteBookmarkGroupList(id) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8080/bookmarkGroup.jsp');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function () {
            if (xhr.status === 200) {
                alert('삭제 성공!');
                location.reload();
            } else {
                alert('삭제 실패');
            }
        };
        xhr.send(encodeURI('id=' + id));
    }
</script>
</body>
</html>
