package com.augmentum.oes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.augmentum.common.JDBCAbstractCallback;
import com.augmentum.common.JDBCTemplate;
import com.augmentum.oes.model.Pagination;
import com.augmentum.oes.model.Question;

public class QuestionDao {
    JDBCTemplate<Question> jdbcTemplate;

    public void setJdbcTemplate(JDBCTemplate<Question> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void creat(final Question que) {

        String sql = "INSERT INTO question "
                + "(question,desciption,option_a,option_b,option_c,option_d,answer,creat_time,update_time)"
                + " VALUES(?,?,?,?,?,?,?,NOW(),NOW())";
        int id = jdbcTemplate.creat(sql, new JDBCAbstractCallback<Question>() {

            @Override
            public void setParams(PreparedStatement stem) throws SQLException {
                stem.setString(1, que.getQuestion());
                stem.setString(2, que.getDesciption());
                stem.setString(3, que.getOptionA());
                stem.setString(4, que.getOptionB());
                stem.setString(5, que.getOptionC());
                stem.setString(6, que.getOptionD());
                stem.setString(7, que.getAnswer());

            }
        });
        que.setId(id);
    }

    public List<Question> query(final Pagination pagination) {
        pagination.setTotalCount(this.getQuestionCount());
        if (pagination.getCurrentPage() > pagination.getPageCount()) {
            pagination.setCurrentPage(pagination.getPageCount());
        }
        String sql = "SELECT * FROM question WHERE `delete`=0 LIMIT ?,?";
        List<Question> questions = jdbcTemplate.query(sql, new JDBCAbstractCallback<Question>() {

            @Override
            public void setParams(PreparedStatement stem) throws SQLException {
                stem.setInt(1, pagination.getOffset());
                stem.setInt(2, pagination.getPageSize());
            }

            @Override
            public Question rsToObject(ResultSet rs) throws SQLException {
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
        return jdbcTemplate.getCount(sql, new JDBCAbstractCallback<Question>() {
        });
    }

    public Question getById(final int id) {
        String sql = "SELECT  * FROM question WHERE id = ?";
        return jdbcTemplate.queryOne(sql, new JDBCAbstractCallback<Question>() {

            @Override
            public void setParams(PreparedStatement stem) throws SQLException {
                stem.setInt(1, id);
            }

            @Override
            public Question rsToObject(ResultSet rs) throws SQLException {
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

    }

    public void update(final Question que) {
        String sql = "UPDATE question SET question = ?, desciption = ?, option_a = ?,"
                + " option_b = ?, option_c = ?, option_d = ?, answer = ?,"
                + " update_time=NOW() WHERE id =" + que.getId();
        jdbcTemplate.update(sql, new JDBCAbstractCallback<Question>() {

            @Override
            public void setParams(PreparedStatement stem) throws SQLException {
                stem.setString(1, que.getQuestion());
                stem.setString(2, que.getDesciption());
                stem.setString(3, que.getOptionA());
                stem.setString(4, que.getOptionB());
                stem.setString(5, que.getOptionC());
                stem.setString(6, que.getOptionD());
                stem.setString(7, que.getAnswer());
            }
        });
    }

    public void deleteByid(final int id) {
        String sql = "update question set `delete`=1, update_time = NOW() where id =?";
        jdbcTemplate.update(sql, new JDBCAbstractCallback<Question>() {

            @Override
            public void setParams(PreparedStatement stem) throws SQLException {
                stem.setInt(1, id);
            }
        });
    }

}