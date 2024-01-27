package com.sc.mission1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

            String sql = " insert into public_wifi "
                    + " ( x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, x_swifi_adres1, x_swifi_adres2, "
                    + " x_swifi_instl_floor, x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, "
                    + " x_swifi_cnstc_year, x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) "
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";

            preparedStatement = connection.prepareStatement(sql);

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

                preparedStatement.executeUpdate();
                count++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }

        return count;
    }

}

