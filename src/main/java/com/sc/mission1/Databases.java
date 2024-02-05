package com.sc.mission1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Databases {
    public static Connection dbConnect() throws IOException {
        Properties props = new Properties();
        InputStream input = Databases.class.getClassLoader().getResourceAsStream("config.properties");
        if (input != null) {
            props.load(input);
        } else {
            throw new FileNotFoundException("config.properties not found");
        }

        String url = props.getProperty("DB_URL");
        String dbUserId = props.getProperty("DB_USER");
        String dbPassword = props.getProperty("DB_PASSWORD");

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

        } catch (SQLException | IOException e) {
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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        searchHistory(lat, lnt);
        return wifiList;
    }

    public void searchHistory(String lat, String lnt) {
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

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
    }

    public List<SearchHistory> searchHistoryList() {
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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return list;
    }

    public void deleteSearchHistory(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "delete from search_wifi " +
                    "where search_wifi_id = ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }

    }

    public Wifi wifiDetail(String mgrNo) {
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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }

        return wifi;
    }

    public List<Wifi> wifiDetailList(String mgrNo, double distance) {
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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }

        return list;
    }

    public int addBookmarkGroup(BookmarkGroup bookmarkGroup) {
        int result = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String checkSql = "SELECT COUNT(*) FROM bookmark_group WHERE order_no = ?;";
            preparedStatement = connection.prepareStatement(checkSql);
            preparedStatement.setInt(1, bookmarkGroup.getOrderNo());
            rs = preparedStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return -1;
            }

            String sql = " insert into bookmark_group(name, order_no, reg_dttm) "
                    + " values (?, ?, ?);";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookmarkGroup.getName());
            preparedStatement.setInt(2, bookmarkGroup.getOrderNo());
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            preparedStatement.setTimestamp(3, timestamp);

            result = preparedStatement.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return result;
    }

    public BookmarkGroup selectBookmarkGroup(int bookmarkGroupId) {
        BookmarkGroup bookmarkGroup = new BookmarkGroup();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "select * " +
                    "from bookmark_group " +
                    "order by bookmark_group_id = ?;";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bookmarkGroupId);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("reg_dttm");
                LocalDateTime regDttm = timestamp.toLocalDateTime();

                LocalDateTime modifyDttm = null;
                Timestamp timestamp2 = rs.getTimestamp("modify_dttm");
                if (timestamp2 != null) {
                    modifyDttm = timestamp2.toLocalDateTime();
                }
                bookmarkGroup.setId(rs.getInt("bookmark_group_id"));
                bookmarkGroup.setName(rs.getString("name"));
                bookmarkGroup.setOrderNo(rs.getInt("order_no"));
                bookmarkGroup.setRegDttm(regDttm);
                if (modifyDttm != null) {
                    bookmarkGroup.setModifyDttm(modifyDttm);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return bookmarkGroup;
    }

    public List<BookmarkGroup> bookmarkGroupList() {
        List<BookmarkGroup> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "select * " +
                    "from bookmark_group " +
                    "order by bookmark_group_id;";

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("reg_dttm");
                LocalDateTime regDttm = timestamp.toLocalDateTime();

                LocalDateTime modifyDttm = null;
                Timestamp timestamp2 = rs.getTimestamp("modify_dttm");
                if (timestamp2 != null) {
                    modifyDttm = timestamp2.toLocalDateTime();
                }
                BookmarkGroup bookmarkGroup = new BookmarkGroup();
                bookmarkGroup.setId(rs.getInt("bookmark_group_id"));
                bookmarkGroup.setName(rs.getString("name"));
                bookmarkGroup.setOrderNo(rs.getInt("order_no"));
                bookmarkGroup.setRegDttm(regDttm);
                if (modifyDttm != null) {
                    bookmarkGroup.setModifyDttm(modifyDttm);
                }
                list.add(bookmarkGroup);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return list;
    }

    public int modifyBookmarkGroup(int id, String name, int orderNo) {
        int num = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String checkSql = "select count(*) from bookmark_group where order_no = ? and bookmark_group_id != ?";

            preparedStatement = connection.prepareStatement(checkSql);
            preparedStatement.setInt(1, orderNo);
            preparedStatement.setInt(2, id);
            rs = preparedStatement.executeQuery();


            if (rs.next() && rs.getInt(1) > 0) {
                return -1;
            }

            String sql2 = "update bookmark_group " +
                    "set name = ?, order_no = ?, modify_dttm = ? " +
                    "where bookmark_group_id = ?;";

            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, orderNo);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(4, id);

            num = preparedStatement.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return num;
    }

    public void deleteBookmarkGroup(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();
            String sql = "delete from bookmark_group where bookmark_group_id = ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }

    }

    public int addBookmark(Bookmark bookmark) {
        int result = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "insert into bookmark (mgr_no, bookmark_group_id, reg_dttm) "
                    + " values (?, ?, ?);";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookmark.getMgrNo());
            preparedStatement.setInt(2, bookmark.getBookmarkGroupId());
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            preparedStatement.setTimestamp(3, timestamp);

            result = preparedStatement.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return result;
    }

    public Bookmark selectBookmark(int bookmarkId) {
        Bookmark bookmark = new Bookmark();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "select * " +
                    "from bookmark " +
                    "order by bookmark_id = ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookmarkId);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("reg_dttm");
                LocalDateTime regDttm = timestamp.toLocalDateTime();


                bookmark.setId(rs.getInt("bookmark_id"));
                bookmark.setMgrNo(rs.getString("mgr_no"));
                bookmark.setBookmarkGroupId(rs.getInt("bookmark_group_id"));
                bookmark.setRegDttm(regDttm);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return bookmark;
    }

    public List<Bookmark> bookmarkList() {
        List<Bookmark> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();

            String sql = "select * " +
                    "from bookmark " +
                    "order by bookmark_id;";

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("reg_dttm");
                LocalDateTime regDttm = timestamp.toLocalDateTime();

                Bookmark bookmark = new Bookmark();
                bookmark.setId(rs.getInt("bookmark_id"));
                bookmark.setMgrNo(rs.getString("mgr_no"));
                bookmark.setBookmarkGroupId(rs.getInt("bookmark_group_id"));
                bookmark.setRegDttm(regDttm);
                list.add(bookmark);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return list;
    }

    public int deleteBookmark(int id) {
        int result = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = Databases.dbConnect();
            String sql = "delete from bookmark where bookmark_id = ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            Databases.close(connection, preparedStatement, rs);
        }
        return result;
    }

}

