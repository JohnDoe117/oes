package com.augmentum.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.augmentum.oes.AppContext;
import com.augmentum.oes.exception.DBException;
import com.augmentum.oes.util.DBUtil;
import com.mysql.jdbc.Statement;

public class JDBCTemplate<T> {
    public List<T> query(String sql, JDBCCallback<T> jdbcCallback) {
        Connection con = null;
        PreparedStatement stem = null;
        ResultSet rs = null;
        List<T> date = new ArrayList<T>();
        boolean needMyClose = false;
        try {
            con = (Connection) AppContext.getAppContext().getObjiect("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                needMyClose = true;
            }
            stem = con.prepareStatement(sql);
            jdbcCallback.setParams(stem);
            rs = stem.executeQuery();
            while (rs.next()) {
                T object = jdbcCallback.rsToObject(rs);
                date.add(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtil.close(rs, stem, null);
            if (needMyClose) {
                DBUtil.close(null, null, con);
            }
        }
        return date;
    }

    public T queryOne(String sql, JDBCCallback<T> jdbcCallback) {
        List<T> data = query(sql, jdbcCallback);
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public void update(String sql, JDBCCallback<T> jdbcCallback) {
        Connection con = null;
        PreparedStatement stem = null;
        boolean needMyClose = false;
        try {
            con = (Connection) AppContext.getAppContext().getObjiect("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                needMyClose = true;
            }
            stem = con.prepareStatement(sql);
            jdbcCallback.setParams(stem);
            stem.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtil.close(null, stem, null);
            if (needMyClose) {
                DBUtil.close(null, null, con);
            }
        }

    }

    public int creat(String sql, JDBCCallback<T> jdbcCallback) {
        Connection con = null;
        PreparedStatement stem = null;
        ResultSet rs = null;
        int id = 0;
        boolean needMyClose = false;
        try {
            con = (Connection) AppContext.getAppContext().getObjiect("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                needMyClose = true;
            }
            stem = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            jdbcCallback.setParams(stem);
            stem.executeUpdate();
            ResultSet generatedKeys = stem.getGeneratedKeys();

            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            rs = stem.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtil.close(rs, stem, null);
            if (needMyClose) {
                DBUtil.close(null, null, con);
            }
        }
        return id;
    }

    public int getCount(String sql, JDBCCallback<T> jdbcCallback) {
        Connection con = null;
        PreparedStatement stem = null;
        ResultSet rs = null;
        int count = 0;
        boolean needMyClose = false;
        try {
            con = (Connection) AppContext.getAppContext().getObjiect("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                needMyClose = true;
            }
            stem = con.prepareStatement(sql);
            rs = stem.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtil.close(rs, stem, null);
            if (needMyClose) {
                DBUtil.close(null, null, con);
            }
        }
        return count;
    }
}
