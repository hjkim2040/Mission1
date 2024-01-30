<%@ page import="com.sc.mission1.Wifi" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sc.mission1.Databases" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>상세 정보</title>
</head>
<body>
<h1>와이파이 상세 정보</h1>
<%@include file="header.jsp" %>
<br/>
<%
    Databases databases = new Databases();
    String distance = request.getParameter("distance");
    String mgrNo = request.getParameter("mgrNo");
    List<Wifi> list = databases.wifiDetailList(mgrNo, Double.parseDouble(distance));
%>
<div style="padding: 10px 0">
    <table>
        <% for (Wifi wifi : list) { %>
        <tr>
            <th>거리(km)</th>
            <td><%=wifi.getDistance()%>
            </td>
        </tr>
        <tr>
            <th>관리번호</th>
            <td><%=wifi.getXSwifiMgrNo()%>
            </td>
        </tr>
        <tr>
            <th>자치구</th>
            <td><%=wifi.getXSwifiWrdofc()%>
            </td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td><%=wifi.getXSwifiMainNm()%>
            </td>
        </tr>
        <tr>
            <th>도로명 주소</th>
            <td><%=wifi.getXSwifiAdres1()%>
            </td>
        </tr>
        <tr>
            <th>상세 주소</th>
            <td><%=wifi.getXSwifiAdres2()%>
            </td>
        </tr>
        <tr>
            <th>설치 위치(층)</th>
            <td><%=wifi.getXSwifiInstlFloor()%>
            </td>
        </tr>
        <tr>
            <th>설치 기관</th>
            <td><%=wifi.getXSwifiInstlMby()%>
            </td>
        </tr>
        <tr>
            <th>설치 유형</th>
            <td><%=wifi.getXSwifiInstlTy()%>
            </td>
        </tr>
        <tr>
            <th>서비스 구분</th>
            <td><%=wifi.getXSwifiSvcSe()%>
            </td>
        </tr>
        <tr>
            <th>망 종류</th>
            <td><%=wifi.getXSwifiCmcwr()%>
            </td>
        </tr>
        <tr>
            <th>설치 년도</th>
            <td><%=wifi.getXSwifiCnstcYear()%>
            </td>
        </tr>
        <tr>
            <th>실내 외 구분</th>
            <td><%=wifi.getXSwifiInoutDoor()%>
            </td>
        </tr>
        <tr>
            <th>WIFI 접속 환경</th>
            <td><%=wifi.getXSwifiRemars3()%>
            </td>
        </tr>
        <tr>
            <th>x좌표</th>
            <td><%=wifi.getLat()%>
            </td>
        </tr>
        <tr>
            <th>y좌표</th>
            <td><%=wifi.getLnt()%>
            </td>
        </tr>
        <tr>
            <th>작업일자</th>
            <td><%=wifi.getWorkDttm()%>
            </td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
