package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import model.DBInfo;

public class SqlConnectionHelper {
    private String domain;
    private String port;
    private String username;
    private String password;
    private String db_name;
    private ResultSet rs = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private Connection connect = null;
    private ResultSetMetaData rsm = null;

    public SqlConnectionHelper(DBInfo dbInfo) {
        db_name = dbInfo.getDb_name();
        domain = dbInfo.getIp();
        port = dbInfo.getPort();
        username = dbInfo.getUsername();
        password = dbInfo.getPassword();

        connect = getConnection();
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(
                    getConnectionString(), username, password);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String getConnectionString() {
//		return "jdbc:mysql://" + domain + ":" + port + "/" + db_name + "?characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes";
        return "jdbc:mysql://" + domain + ":" + port + "/" + db_name + "?characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=true";
//		return "jdbc:mysql://" + domain + ":" + port + "/" + db_name + "?useUnicode=true";
    }

    public ArrayList<Map<String, Object>> getTable(String sql) {
        ArrayList<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery(sql);
            rsm = rs.getMetaData();
            while (rs.next()) {

                Map<String, Object> columns = new LinkedHashMap<String, Object>();

                for (int i = 1; i <= rsm.getColumnCount(); i++) {
                    columns.put(rsm.getColumnLabel(i), rs.getObject(i));
                }
                rows.add(columns);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }

        return rows;
    }

    public ArrayList<Map<String, Object>> getTable(String sql, ArrayList<Object> values) {
        ArrayList<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        try {
            pstmt = connect.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i) instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values.get(i));
                } else if (values.get(i) instanceof String) {
                    pstmt.setString(i + 1, String.valueOf(values.get(i)));
                } else {
                    pstmt.setObject(i + 1, String.valueOf(values.get(i)));
                }

            }
            rs = pstmt.executeQuery();
            rsm = rs.getMetaData();
            while (rs.next()) {

                Map<String, Object> columns = new LinkedHashMap<String, Object>();

                for (int i = 1; i <= rsm.getColumnCount(); i++) {
                    columns.put(rsm.getColumnLabel(i), rs.getObject(i));
                }
                rows.add(columns);
            }
            System.out.println(pstmt);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }

        return rows;
    }


    public ResultSet getResultSet(String sql) {
        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    public boolean insertValue(String sql, ArrayList<Object> values) {
        try {
            pstmt = connect.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i) instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values.get(i));
                } else if (values.get(i) instanceof String) {
                    pstmt.setString(i + 1, String.valueOf(values.get(i)));
                } else {
                    pstmt.setObject(i + 1, String.valueOf(values.get(i)));
                }

            }
            pstmt.executeUpdate();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return false;
        } finally {

        }


        return true;
    }

    public boolean updateValue(String sql, ArrayList<Object> values) {
        try {
            pstmt = connect.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i) instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values.get(i));
                } else if (values.get(i) instanceof String) {
                    pstmt.setString(i + 1, String.valueOf(values.get(i)));
                } else {
                    pstmt.setObject(i + 1, String.valueOf(values.get(i)));
                }

            }
            System.out.println(pstmt);
            pstmt.executeUpdate();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return false;
        } finally {

        }
        return true;
    }


    public Object getSingleValue(String sql) {
        Object value = null;
        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery(sql);
            rsm = rs.getMetaData();
            while (rs.next()) {
                value = rs.getObject(1);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }
        return value;
    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (connect != null) {
                connect.close();
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}



