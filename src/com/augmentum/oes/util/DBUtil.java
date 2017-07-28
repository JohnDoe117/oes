package com.augmentum.oes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.augmentum.oes.exception.DBException;

public class DBUtil {
    public static Connection getConnection() {
        try {
            Class.forName(PropertiesUtil.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = PropertiesUtil.getProperty("jdbc.url");
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, PropertiesUtil.getProperty("jdbc.user"),
                    PropertiesUtil.getProperty("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }

        return con;
    }

    public static void close(ResultSet rs, PreparedStatement stem, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stem != null) {
                stem.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setAutoCommit(Connection conn, boolean autoCommit) {
        try {
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }
    }

    public static void commit(Connection conn) {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }
    }

    public static void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }
    }
}
