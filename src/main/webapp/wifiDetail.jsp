<%@ page import="com.sc.mission1.dto.Wifi" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sc.mission1.serivce.Databases" %>
<%@ page import="com.sc.mission1.dto.BookmarkGroup" %>
<%@ page import="com.sc.mission1.dto.Bookmark" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        th {
            width: 25%;
        }
    </style>
</head>
<body>
<h1>와이파이 상세 정보</h1>
<%@include file="header.jsp" %>
<%
    Databases databases = new Databases();
    String distance = request.getParameter("distance");
    String mgrNo = request.getParameter("mgrNo");

    List<Wifi> wifilist = databases.wifiDetailList(mgrNo, Double.parseDouble(distance));
    List<BookmarkGroup> bookmarkGroupList = databases.bookmarkGroupList();
    request.setAttribute("bookmarkGroupList", bookmarkGroupList);

    request.setCharacterEncoding("UTF-8");
    String message = "";
    Bookmark bookmark = new Bookmark();

    if (request.getMethod().equalsIgnoreCase("POST")) {
        int bookmarkGroupNo = Integer.parseInt(request.getParameter("bookmark_group_id"));

        bookmark.setMgrNo(mgrNo);
        bookmark.setBookmarkGroupId(bookmarkGroupNo);
        int result = databases.addBookmark(bookmark);

        if (result > 0) {
            message = "추가 성공!";
        } else {
            message = "추가 실패";
        }
    } else {
%>
<form method="post" id="add-form">
    <select name="bookmark_group_id">
        <option value="">북마크 그룹 이름 선택</option>
        <% for (BookmarkGroup group : bookmarkGroupList) { %>
        <option value="<%= group.getId() %>"><%= group.getName() %>
        </option>
        <% } %>
    </select>
    <button type="submit">북마크 추가하기</button>
</form>
<%
    }
%>
<div style="padding: 10px 0">
    <table>
        <% for (Wifi wifi : wifilist) { %>
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
<script>
    var message = "<%= message %>";
    if (message) {
        alert(message);
        location.href = "bookmark.jsp";
    }
</script>
</body>
</html>
