package com.sc.mission1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class Databases {
    public static Connection dbConnect() {
        String url = "jdbc:mariadb://13.209.241.155:3306/mission1";
        String dbUserId = "mission1_user";
        String dbPassword = "mission1";

        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet rs) {

        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insertWifi(List<Wifi> infoList) {
        int count = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = " insert ignore into public_wifi "
                    + " ( x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, x_swifi_adres1, x_swifi_adres2, "
                    + " x_swifi_instl_floor, x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, "
                    + " x_swifi_cnstc_year, x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) "
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";

            preparedStatement = connection.prepareStatement(sql);

            int i = 0;

            for (Wifi wifi : infoList) {
                preparedStatement.setString(1, wifi.getXSwifiMgrNo());
                preparedStatement.setString(2, wifi.getXSwifiWrdofc());
                preparedStatement.setString(3, wifi.getXSwifiMainNm());
                preparedStatement.setString(4, wifi.getXSwifiAdres1());
                preparedStatement.setString(5, wifi.getXSwifiAdres2());
                preparedStatement.setString(6, wifi.getXSwifiInstlFloor());
                preparedStatement.setString(7, wifi.getXSwifiInstlTy());
                preparedStatement.setString(8, wifi.getXSwifiInstlMby());
                preparedStatement.setString(9, wifi.getXSwifiSvcSe());
                preparedStatement.setString(10, wifi.getXSwifiCmcwr());
                preparedStatement.setString(11, wifi.getXSwifiCnstcYear());
                preparedStatement.setString(12, wifi.getXSwifiInoutDoor());
                preparedStatement.setString(13, wifi.getXSwifiRemars3());
                preparedStatement.setString(14, wifi.getLat());
                preparedStatement.setString(15, wifi.getLnt());
                preparedStatement.setString(16, wifi.getWorkDttm());

                preparedStatement.addBatch();
                i++;

                if (i % 1000 == 0 || i == infoList.size()) {
                    preparedStatement.executeBatch();
                }
                count++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }

        return count;
    }

    public List<Wifi> getNearWifiList(String lat, String lnt) {
        List<Wifi> wifiList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "SELECT *, ROUND(( 6371 * acos( cos( radians(?) ) * cos( radians( lat ) ) * " +
                    "cos( radians( lnt ) - radians(?) ) + sin( radians(?) ) * sin( radians( lat ) ) ) ), 4) " +
                    "AS distance FROM public_wifi ORDER BY distance LIMIT 20;";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, Double.parseDouble(lat));
            preparedStatement.setDouble(2, Double.parseDouble(lnt));
            preparedStatement.setDouble(3, Double.parseDouble(lat));

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Wifi wifi = new Wifi();
                wifi.setDistance(rs.getDouble("distance"));
                wifi.setXSwifiMgrNo(rs.getString("x_swifi_mgr_no"));
                wifi.setXSwifiWrdofc(rs.getString("x_swifi_wrdofc"));
                wifi.setXSwifiMainNm(rs.getString("x_swifi_main_nm"));
                wifi.setXSwifiAdres1(rs.getString("x_swifi_adres1"));
                wifi.setXSwifiAdres2(rs.getString("x_swifi_adres2"));
                wifi.setXSwifiInstlFloor(rs.getString("x_swifi_instl_floor"));
                wifi.setXSwifiInstlTy(rs.getString("x_swifi_instl_ty"));
                wifi.setXSwifiInstlMby(rs.getString("x_swifi_instl_mby"));
                wifi.setXSwifiSvcSe(rs.getString("x_swifi_svc_se"));
                wifi.setXSwifiCmcwr(rs.getString("x_swifi_cmcwr"));
                wifi.setXSwifiCnstcYear(rs.getString("x_swifi_cnstc_year"));
                wifi.setXSwifiInoutDoor(rs.getString("x_swifi_inout_door"));
                wifi.setXSwifiRemars3(rs.getString("x_swifi_remars3"));
                wifi.setLat(rs.getString("lat"));
                wifi.setLnt(rs.getString("lnt"));
                wifi.setWorkDttm(rs.getString("work_dttm"));
                wifiList.add(wifi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        searchHistory(lat, lnt);
        return wifiList;
    }
    public void searchHistory (String lat, String lnt) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "insert into search_wifi " +
                    "(lat, lnt, search_dttm) " +
                    "values (?, ?, ?);";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, lat);
            preparedStatement.setString(2, lnt);
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            preparedStatement.setTimestamp(3, timestamp);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
    }
    public List<SearchHistory> searchHistoryList () {
        List<SearchHistory> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "select * " +
                    "from search_wifi " +
                    "order by search_wifi_id desc;";

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("search_dttm");

                LocalDateTime searchDttm = timestamp.toLocalDateTime();

                SearchHistory searchHistory = new SearchHistory(
                        rs.getInt("search_wifi_id"),
                        rs.getString("lat"),
                        rs.getString("lnt"),
                        searchDttm
                );
                list.add(searchHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return list;
    }
    public void deleteSearchHistory (String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "delete from search_wifi " +
                    "where search_wifi_id = ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }

    }
    public Wifi wifiDetail (String mgrNo) {
        Wifi wifi = new Wifi();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "select * from public_wifi where x_swifi_mgr_no = ?;";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, mgrNo);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                wifi.setXSwifiMgrNo(rs.getString("x_swifi_mgr_no"));
                wifi.setXSwifiWrdofc(rs.getString("x_swifi_wrdofc"));
                wifi.setXSwifiMainNm(rs.getString("x_swifi_main_nm"));
                wifi.setXSwifiAdres1(rs.getString("x_swifi_adres1"));
                wifi.setXSwifiAdres2(rs.getString("x_swifi_adres2"));
                wifi.setXSwifiInstlFloor(rs.getString("x_swifi_instl_floor"));
                wifi.setXSwifiInstlTy(rs.getString("x_swifi_instl_ty"));
                wifi.setXSwifiInstlMby(rs.getString("x_swifi_instl_mby"));
                wifi.setXSwifiSvcSe(rs.getString("x_swifi_svc_se"));
                wifi.setXSwifiCmcwr(rs.getString("x_swifi_cmcwr"));
                wifi.setXSwifiCnstcYear(rs.getString("x_swifi_cnstc_year"));
                wifi.setXSwifiInoutDoor(rs.getString("x_swifi_inout_door"));
                wifi.setXSwifiRemars3(rs.getString("x_swifi_remars3"));
                wifi.setLat(rs.getString("lat"));
                wifi.setLnt(rs.getString("lnt"));
                wifi.setWorkDttm(rs.getString("work_dttm"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }

        return wifi;
    }
    public List<Wifi> wifiDetailList (String mgrNo, double distance) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        List<Wifi> list = new ArrayList<>();

        try {
            connection = Databases.dbConnect();

            String sql = "select * from public_wifi where x_swifi_mgr_no = ?;";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, mgrNo);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Wifi wifi = new Wifi();
                wifi.setDistance(distance);
                wifi.setXSwifiMgrNo(rs.getString("x_swifi_mgr_no"));
                wifi.setXSwifiWrdofc(rs.getString("x_swifi_wrdofc"));
                wifi.setXSwifiMainNm(rs.getString("x_swifi_main_nm"));
                wifi.setXSwifiAdres1(rs.getString("x_swifi_adres1"));
                wifi.setXSwifiAdres2(rs.getString("x_swifi_adres2"));
                wifi.setXSwifiInstlFloor(rs.getString("x_swifi_instl_floor"));
                wifi.setXSwifiInstlTy(rs.getString("x_swifi_instl_ty"));
                wifi.setXSwifiInstlMby(rs.getString("x_swifi_instl_mby"));
                wifi.setXSwifiSvcSe(rs.getString("x_swifi_svc_se"));
                wifi.setXSwifiCmcwr(rs.getString("x_swifi_cmcwr"));
                wifi.setXSwifiCnstcYear(rs.getString("x_swifi_cnstc_year"));
                wifi.setXSwifiInoutDoor(rs.getString("x_swifi_inout_door"));
                wifi.setXSwifiRemars3(rs.getString("x_swifi_remars3"));
                wifi.setLat(rs.getString("lat"));
                wifi.setLnt(rs.getString("lnt"));
                wifi.setWorkDttm(rs.getString("work_dttm"));
                list.add(wifi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }

        return list;
    }

}

