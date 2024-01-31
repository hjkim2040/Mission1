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
<%@include file="header.jsp"%>
<div>
    북마크를 삭제하시겠습니까?
</div>
<div style="margin-top: 20px">
    <table>
        <tr>
            <th>북마크 이름</th>
            <td></td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td></td>
        </tr>
        <tr>
            <th>등록일자</th>
            <td></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <a href="">돌아가기</a>
                <a>|</a>
                <button>삭제</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
