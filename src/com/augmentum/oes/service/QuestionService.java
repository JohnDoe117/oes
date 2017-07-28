package com.augmentum.oes.service;

import java.util.List;

import com.augmentum.oes.dao.QuestionDao;
import com.augmentum.oes.exception.ParameterException;
import com.augmentum.oes.model.Pagination;
import com.augmentum.oes.model.Question;

public interface QuestionService {

    public void setQuestionDao(QuestionDao questionDao);

    public int creat(Question que) throws ParameterException;

    public List<Question> query(Pagination pagination);

    public Question getById(int id);

    public void deleteById(int id);
}
