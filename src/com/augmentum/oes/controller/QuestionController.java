package com.augmentum.oes.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.common.ModelAndView;
import com.augmentum.oes.AppContext;
import com.augmentum.oes.Oes;
import com.augmentum.oes.exception.ParameterException;
import com.augmentum.oes.model.Pagination;
import com.augmentum.oes.model.Question;
import com.augmentum.oes.service.QuestionService;

public class QuestionController {
    private QuestionService questionService;

    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public ModelAndView create(Map<String, String> request, Map<String, Object> session) {
        ModelAndView modelAndView = new ModelAndView();
        String question = request.get("question");
        String desciption = request.get("question");
        String option_a = request.get("option_a");
        String option_b = request.get("option_b");
        String option_c = request.get("option_c");
        String option_d = request.get("option_d");
        String answer = request.get("answer");
        String idStr = request.get("id");
        int id = Integer.parseInt(idStr);
        Question que = new Question();
        que.setId(id);
        que.setQuestion(question);
        que.setDesciption(desciption);
        que.setOptionA(option_a);
        que.setOptionB(option_b);
        que.setOptionC(option_c);
        que.setOptionD(option_d);
        que.setAnswer(answer);
        try {
            // QuestionService createQuestionService = new QuestionService();
            questionService.creat(que);
            modelAndView.addSessionAttribute("SUCESS_FLASH_MESSAGE", "createquestion");
            modelAndView.setIsRedirect(true);
            modelAndView.setView("/questionlist.action");
        } catch (ParameterException parameterException) {
            Map<String, String> errorFileMap = parameterException.getErrorFileMap();
            modelAndView.addObject("ERRORMAP", errorFileMap);
            modelAndView.addObject("question", que);
            modelAndView.setView("/WEB-INF/jsp/question/createquestion.jsp");
        }
        return modelAndView;
    }

    public ModelAndView delete(Map<String, String> request, Map<String, Object> session) {
        ModelAndView modelAndView = new ModelAndView();
        HttpServletRequest httpRequest = (HttpServletRequest) AppContext.getAppContext()
                .getObjiect(Oes.APP_CONTEXT_REQUEST);
        String[] deleteId = httpRequest.getParameterValues("choseDelete");
        if (deleteId != null && deleteId.length > 0) {
            int[] deleteIdInt = new int[deleteId.length];
            int index = 0;
            for (String deleteIdStr : deleteId) {
                deleteIdInt[index] = Integer.parseInt(deleteIdStr);
                index++;
            }

            for (int id : deleteIdInt) {
                questionService.deleteById(id);
            }
        }

        modelAndView.setView("/questionlist.action");
        return modelAndView;
    }

    public ModelAndView list(Map<String, String> request, Map<String, Object> session) {
        ModelAndView modelAndView = new ModelAndView();
        String currentPageStr = request.get("currentPage");
        if (currentPageStr == null || "".equals(currentPageStr)) {
            currentPageStr = "1";
        }

        int currentPage = Integer.parseInt(currentPageStr);
        if (currentPage < 1) {
            currentPage = 1;
        }

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(currentPage);
        List<Question> questions = questionService.query(pagination);
        modelAndView.addObject("questions", questions);
        modelAndView.addObject("pagination", pagination);
        modelAndView.setView("/WEB-INF/jsp/question/questionlist.jsp");
        return modelAndView;
    }

    public ModelAndView edit(Map<String, String> request, Map<String, Object> session) {
        String idStr = request.get("id");
        ModelAndView modelAndView = new ModelAndView();
        Question question = null;
        if (idStr == null || "".equals(idStr)) {
            question = new Question();
        } else {
            int id = Integer.parseInt(idStr);
            // QuestionService questionService = new QuestionService();
            question = questionService.getById(id);
        }

        modelAndView.addObject("question", question);
        modelAndView.setView("/WEB-INF/jsp/question/createquestion.jsp");
        return modelAndView;
    }

}
