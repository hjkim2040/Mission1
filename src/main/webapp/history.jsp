<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>위치 히스토리 목록</title>
</head>
<body>
<h1><%= "위치 히스토리 목록" %></h1>
<%@include file="header.jsp" %>
<br/>
<br/>
<table>
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    <%--  <tr>--%>
    <%--    <td><%= wifiInfo.distance %></td>--%>
    <%--    <td><%= wifiInfo.xSwifiMgrNo %></td>--%>
    <%--    <td><%= wifiInfo.xSwifiWrdofc %></td>--%>
    <%--    <!-- 나머지 데이터 -->--%>
    <%--    <td><%= wifiInfo.workDttm %></td>--%>
    <%--  </tr>--%>
    <%--  <!-- 나머지 행 -->--%>
</table>
</body>
</html>
