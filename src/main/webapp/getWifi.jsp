<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.sc.mission1.WifiApi" %>
<html>
<head>
    <title>와이파이 데이터 요청</title>
    <style>
        .center {
            text-align: center;
        }
    </style>
</head>
<body>
<% WifiApi wifiApi = new WifiApi();
    int count = wifiApi.getPublicWifiJson();
%>
<div class="center">
    <h1><%=count %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
    <a href="index.jsp">홈 으로 가기</a>
</div>

</body>
</html>
