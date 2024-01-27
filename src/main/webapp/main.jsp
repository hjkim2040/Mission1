<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
LAT : <input type="text" name="LAT" value="0.0"> , LNT : <input type="text" name="LAT" value="0.0"> <button type="button">내 위치 가져오기</button> <button type="button">근처 WIPI 정보 보기</button>
<br/>
<br/>
<table>
    <tr>
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    <tr>
        <td colspan="17">위치정보를 입력한 후에 조회해 주세요.</td>
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
