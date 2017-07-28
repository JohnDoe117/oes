package com.augmentum.oes.service.impl;

import java.util.List;

import com.augmentum.oes.dao.QuestionDao;
import com.augmentum.oes.exception.ParameterException;
import com.augmentum.oes.model.Pagination;
import com.augmentum.oes.model.Question;
import com.augmentum.oes.service.QuestionService;

public class QuestionServiceImpl implements QuestionService {
    private QuestionDao questionDao;

    @Override
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public int creat(Question que) throws ParameterException {
        ParameterException exception = new ParameterException();
        if (que.getQuestion() == null || que.getQuestion().equals("")) {
            exception.adderrorFile("question", "question in requirzed!");
        }

        if (que.getOptionA() == null || que.getOptionA().equals("")) {
            exception.adderrorFile("option_a", "option_a in requirzed!");
        }

        if (que.getOptionB() == null || que.getOptionB().equals("")) {
            exception.adderrorFile("option_b", "option_b in requirzed!");
        }

        if (que.getOptionC() == null || que.getOptionC().equals("")) {
            exception.adderrorFile("option_c", "option_c in requirzed!");
        }

        if (que.getOptionD() == null || que.getOptionD().equals("")) {
            exception.adderrorFile("option_d", "option_d in requirzed!");
        }

        if (que.getAnswer() == null || que.getAnswer().equals("")) {
            exception.adderrorFile("answer", "answer in requirzed!");
        }

        if (exception.isErrorField()) {
            throw exception;
        }

        if (que.getId() > 0) {
            questionDao.update(que);
        } else {
            questionDao.creat(que);
        }
        return que.getId();
    }

    @Override
    public List<Question> query(Pagination pagination) {
        return questionDao.query(pagination);
    }

    @Override
    public Question getById(int id) {
        return questionDao.getById(id);
    }

    @Override
    public void deleteById(int id) {
        questionDao.deleteByid(id);
    }

}
