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
<%@include file="header.jsp"%>
<div>
    <table>
        <tr>
            <th>북마크 이름</th>
            <td><input type="text" value=""></td>
        </tr>
        <tr>
            <th>순서</th>
            <td><input type="text" value=""></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <a href="">돌아가기</a>
                <a>|</a>
                <button>수정</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
