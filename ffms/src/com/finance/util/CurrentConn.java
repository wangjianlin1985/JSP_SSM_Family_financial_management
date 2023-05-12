// 
// 
// 

package com.finance.util;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.InputStream;
import java.util.Properties;

public class CurrentConn
{
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    private static CurrentConn CONN;
    
    static {
        CurrentConn.CONN = null;
        final Properties prop = new Properties();
        try {
            final InputStream is = CurrentConn.class.getResourceAsStream("/db.properties");
            prop.load(is);
            CurrentConn.driver = prop.getProperty("driver");
            CurrentConn.url = prop.getProperty("url");
            CurrentConn.username = prop.getProperty("username");
            CurrentConn.password = prop.getProperty("password");
            is.close();
            Class.forName(CurrentConn.driver);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static synchronized CurrentConn getInstance() {
        if (CurrentConn.CONN == null) {
            CurrentConn.CONN = new CurrentConn();
        }
        return CurrentConn.CONN;
    }
    
    public static Connection getConn() {
        try {
            final Connection conn = DriverManager.getConnection(CurrentConn.url, CurrentConn.username, CurrentConn.password);
            return conn;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeConnection(final Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void closePreparedStatement(final PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
