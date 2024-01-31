<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<h1>북마크 목록</h1>
<%@include file="header.jsp" %>
<div>
    <table>
        <tr>
            <th>ID</th>
            <th>북마크 이름</th>
            <th>와이파이명</th>
            <th>등록일자</th>
            <th>비고</th>
        </tr>
        <tr>
            <td>ID</td>
            <td>북마크 이름</td>
            <td>와이파이명</td>
            <td>등록일자</td>
            <td style="text-align: center;"><a href="bookmarkDelete.jsp">삭제</a></td>
        </tr>
    </table>
</div>
</body>
</html>
