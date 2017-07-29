package com.augmentum.oes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.augmentum.oes.model.Pagination;
import com.augmentum.oes.model.Question;

public class QuestionDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void creat(final Question que) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                String sql = "INSERT INTO book(name, picture, owner_id, owner_name, current_owner_id, current_owner_name, author, description, created_time, updated_time) "
                        +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW()) ";
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, que.getQuestion());
                stmt.setString(2, que.getDesciption());
                stmt.setString(3, que.getOptionA());
                stmt.setString(4, que.getOptionB());
                stmt.setString(5, que.getOptionC());
                stmt.setString(6, que.getOptionD());
                stmt.setString(7, que.getAnswer());
                return stmt;
            }
        }, keyHolder);

        que.setId(keyHolder.getKey().intValue());
    }

    public List<Question> query(final Pagination pagination) {
        pagination.setTotalCount(this.getQuestionCount());
        if (pagination.getCurrentPage() > pagination.getPageCount()) {
            pagination.setCurrentPage(pagination.getPageCount());
        }
        final String sql = "SELECT * FROM question WHERE `delete`=0 LIMIT ?,?";
        final Object[] args = new Object[] { pagination.getOffset(), pagination.getPageSize() };
        List<Question> questions = jdbcTemplate.query(sql, args, new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setQuestion(rs.getString("question"));
                question.setDesciption(rs.getString("desciption"));
                question.setOptionA(rs.getString("option_a"));
                question.setOptionB(rs.getString("option_b"));
                question.setOptionC(rs.getString("option_c"));
                question.setOptionD(rs.getString("option_d"));
                question.setAnswer(rs.getString("answer"));
                question.setCreat_timeDate(rs.getDate("creat_time"));
                question.setUpdate_time(rs.getDate("update_time"));
                return question;
            }
        });
        return questions;
    }

    private int getQuestionCount() {
        String sql = "SELECT count(id) FROM question";
        return jdbcTemplate.queryForInt(sql);
    }

    public Question getById(final int id) {
        String sql = "SELECT  * FROM question WHERE id = ?";
        Object[] args = new Object[] { id };
        RowMapper<Question> rowMapper = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setQuestion(rs.getString("question"));
                question.setDesciption(rs.getString("desciption"));
                question.setOptionA(rs.getString("option_a"));
                question.setOptionB(rs.getString("option_b"));
                question.setOptionC(rs.getString("option_c"));
                question.setOptionD(rs.getString("option_d"));
                question.setAnswer(rs.getString("answer"));
                question.setCreat_timeDate(rs.getDate("creat_time"));
                question.setUpdate_time(rs.getDate("update_time"));
                return question;
            }
        };

        Question question = jdbcTemplate.queryForObject(sql, args, rowMapper);
        return question;

    }

    public void update(final Question que) {
        String sql = "UPDATE question SET question = ?, desciption = ?, option_a = ?,"
                + " option_b = ?, option_c = ?, option_d = ?, answer = ?,"
                + " update_time=NOW() WHERE id =" + que.getId();

        Object[] args = new Object[] { que.getQuestion(), que.getDesciption(), que.getOptionA(), que.getOptionB(),
                que.getOptionC(), que.getOptionD(), que.getAnswer() };
        jdbcTemplate.update(sql, args);
    }

    public void deleteByid(final int id) {
        String sql = "update question set `delete`=1, update_time = NOW() where id =?";
        Object[] args = new Object[] { id };
        jdbcTemplate.update(sql, args);

    }

}