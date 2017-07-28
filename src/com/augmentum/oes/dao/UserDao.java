package com.augmentum.oes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.augmentum.common.JDBCAbstractCallback;
import com.augmentum.common.JDBCTemplate;
import com.augmentum.oes.model.User;

public class UserDao {
    JDBCTemplate<User> jdbcTemplate;

    public void setJdbcTemplate(JDBCTemplate<User> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUser(final String userName) {
        if (userName == null || userName.equals("")) {
            return null;
        }

        return jdbcTemplate.queryOne("SELECT * FROM `user` WHERE name=?", new JDBCAbstractCallback<User>() {
            @Override
            public void setParams(PreparedStatement stem) throws SQLException {
                stem.setString(1, userName);
            }

            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });
    }
}
