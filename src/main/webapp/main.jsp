<%@ page import="com.sc.mission1.Wifi" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sc.mission1.Databases" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
<%
    String lat = request.getParameter("lat") == null ? "0.0" : request.getParameter("lat");
    String lnt = request.getParameter("lnt") == null ? "0.0" : request.getParameter("lnt");
%>
<div style="display: flex; align-items: center; padding: 10px 0">
    <span>LAT : </span><input type="text" id="LAT" value=<%=lat%>>
    <span>, LNT : </span><input type="text" id="LNT" value=<%=lnt%>>
    <button type="button" onclick="getLocation()">내 위치 가져오기</button>
    <button type="button" onclick="getNearWifiInfo()">근처 WIPI 정보 보기</button>
</div>
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
    <%
        if (!("0.0").equals(lat) && !("0.0").equals(lnt)) {
            Databases databases = new Databases();
            List<Wifi> wifiList = databases.getNearWifiList(lat, lnt);

            if (!wifiList.isEmpty()) {
                for (Wifi wf : wifiList) {
    %>
    <tr>
        <td><%=wf.getDistance()%>
        </td>
        <td><%=wf.getXSwifiMgrNo()%>
        </td>
        <td><%=wf.getXSwifiWrdofc()%>
        </td>
        <td><a href="wifiDetail.jsp?mgrNo=<%=wf.getXSwifiMgrNo()%>&distance=<%=wf.getDistance()%>"><%=wf.getXSwifiMainNm()%></a>
        </td>
        <td><%=wf.getXSwifiAdres1()%>
        </td>
        <td><%=wf.getXSwifiAdres2()%>
        </td>
        <td><%=wf.getXSwifiInstlFloor()%>
        </td>
        <td><%=wf.getXSwifiInstlMby()%>
        </td>
        <td><%=wf.getXSwifiInstlTy()%>
        </td>
        <td><%=wf.getXSwifiSvcSe()%>
        </td>
        <td><%=wf.getXSwifiCmcwr()%>
        </td>
        <td><%=wf.getXSwifiCnstcYear()%>
        </td>
        <td><%=wf.getXSwifiInoutDoor()%>
        </td>
        <td><%=wf.getXSwifiRemars3()%>
        </td>
        <td><%=wf.getLat()%>
        </td>
        <td><%=wf.getLnt()%>
        </td>
        <td><%=wf.getWorkDttm()%>
        </td>
    </tr>
    <% }
    }
    } else { %>
        <td colspan="17" style="height:50px">위치정보를 입력한 후에 조회해 주세요.</td>
    <% } %>
</table>
<script>
    var x = document.getElementById("LAT");
    var y = document.getElementById("LNT");

    function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        } else {
            x.value = "찾을 수 없습니다."
            y.value = "찾을 수 없습니다."
        }
    }

    function showPosition(position) {
        x.value = position.coords.latitude;
        y.value = position.coords.longitude;
    }

    function getNearWifiInfo() {
        var lat = document.getElementById('LAT').value;
        var lnt = document.getElementById('LNT').value;

        if (lat === '0.0' && lnt === '0.0') {
            alert('위치정보를 입력한 후에 조회해 주세요.');
            return;
        }
        var url = 'http://localhost:8080/?lat=' + lat + '&lnt=' + lnt;

        window.location.href = url;
    }
</script>
</body>
</html>
