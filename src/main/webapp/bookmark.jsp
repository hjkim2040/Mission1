<%@ page import="com.sc.mission1.Bookmark" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sc.mission1.Databases" %>
<%@ page import="com.sc.mission1.Wifi" %>
<%@ page import="com.sc.mission1.BookmarkGroup" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<h1>북마크 목록</h1>
<%@include file="header.jsp" %>
<%
    Databases databases = new Databases();
%>
<div>
    <table>
        <tr>
            <th>ID</th>
            <th>북마크 이름</th>
            <th>와이파이명</th>
            <th>등록일자</th>
            <th>비고</th>
        </tr>
        <%
            List<Bookmark> bookmarkList = databases.bookmarkList();
            if (bookmarkList.isEmpty()) {

        %>
        <tr>
            <td colspan="5" style="text-align: center; height: 50px;">데이터가 존재하지 않습니다.</td>
        </tr>
        <%
        } else {
            for (Bookmark bookmark : bookmarkList) {
                Wifi wifi = databases.wifiDetail(bookmark.getMgrNo());
                BookmarkGroup bookmarkGroup = databases.selectBookmarkGroup(bookmark.getBookmarkGroupId());
        %>
        <tr>
            <td><%=bookmark.getId()%>
            </td>
            <td><%=bookmarkGroup.getName()%>
            </td>
            <td><a href="wifiDetail.jsp?mgrNo=<%=wifi.getXSwifiMgrNo()%>"></a><%=wifi.getXSwifiMainNm()%>
            </td>
            <td><%=bookmark.getRegDttm()%>
            </td>
            <td style="text-align: center;"><a href="bookmarkDelete.jsp?id=<%=bookmark.getId()%>">삭제</a></td>
        </tr>
        <% }
        } %>
    </table>
</div>
</body>
</html>
