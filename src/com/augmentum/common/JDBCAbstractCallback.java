package com.augmentum.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JDBCAbstractCallback<T> implements JDBCCallback<T> {
    @Override
    public T rsToObject(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public void setParams(PreparedStatement stem) throws SQLException {

    }
}
