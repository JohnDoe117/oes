package com.augmentum.oes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.augmentum.oes.model.User;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUser(final String userName) {
        if (userName == null || userName.equals("")) {
            return null;
        }
        String sql = "SELECT * FROM `user` WHERE name=?";
        Object[] args = new Object[] { userName };
        return jdbcTemplate.queryForObject(sql, args, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });
    }
}
