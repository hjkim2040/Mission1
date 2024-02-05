<%@ page import="com.sc.mission1.SearchHistory" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sc.mission1.Databases" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<h1><%= "위치 히스토리 목록" %>
</h1>
<%@include file="header.jsp" %>
<%
    Databases databases = new Databases();
    List<SearchHistory> list = databases.searchHistoryList();

    String id = request.getParameter("id");
    if (id != null) {
        databases.deleteSearchHistory(Integer.parseInt(id));
    }
%>

<div>
    <table>
        <tr>
            <th>ID</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>조회일자</th>
            <th>비고</th>
        </tr>
        <%
            if (list.isEmpty()) {
        %>
        <tr>
            <td colspan="5" style="text-align: center; height:50px">조회 이력이 없습니다.</td>
        </tr>
        <% } else {
            for (SearchHistory searchHistory : list) { %>
        <tr>
            <td><%=searchHistory.getId()%>
            </td>
            <td><%=searchHistory.getLat()%>
            </td>
            <td><%=searchHistory.getLnt()%>
            </td>
            <td><%=searchHistory.getSearchDttm()%>
            </td>
            <td style="text-align: center;">
                <button onclick="deleteSearchHistory(<%=searchHistory.getId()%>)">삭제</button>
            </td>
        </tr>
        <% }
        } %>
    </table>
</div>
<script>
    function deleteSearchHistory(id) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8080/searchHistory.jsp');
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
