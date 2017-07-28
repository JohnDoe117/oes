package com.augmentum.oes.controller;

import java.util.Map;

import com.augmentum.common.ModelAndView;
import com.augmentum.oes.Oes;
import com.augmentum.oes.exception.ParameterException;
import com.augmentum.oes.exception.ServiceException;
import com.augmentum.oes.model.User;
import com.augmentum.oes.service.UserService;
import com.augmentum.oes.util.StringUtil;

public class UserController {
    private static final String LOGIN_PAGE = "/WEB-INF/jsp/question/login.jsp";
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ModelAndView login(Map<String, String> request, Map<String, Object> session) {
        User user = (User) session.get(Oes.USER);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null) {
            modelAndView.setIsRedirect(true);
            modelAndView.setView("/questionlist.action");
        } else {
            String go = request.get("go");
            if (go == null || "".equals(go)) {
                go = "";
            }
            modelAndView.addObject("go", go);
            modelAndView.setView(LOGIN_PAGE);
        }
        return modelAndView;
    }

    public ModelAndView savelogin(Map<String, String> request, Map<String, Object> session) {
        String userName = request.get("userName");
        String password = request.get("password");
        ModelAndView modelAndView = new ModelAndView();
        try {
            User user = null;
            // UserService userService = new UserService();
            user = userService.login(userName, password);
            user.setPassword(null);
            modelAndView.addSessionAttribute(Oes.USER, user);
            String go = request.get("go");
            String queryString = request.get("queryString");
            if (!StringUtil.isEmpty(queryString)) {
                if (queryString.startsWith("#")) {
                    queryString = queryString.substring(1);
                }
                go = go + "?" + queryString;
            }

            modelAndView.setIsRedirect(true);
            String uri = StringUtil.isEmpty(go) ? "questionlist.action" : go;
            modelAndView.setView(uri);
        } catch (ServiceException serviceExpetion) {
            modelAndView.addObject(Oes.TIP_MESSAGE, serviceExpetion.getMessage());
            modelAndView.setView(LOGIN_PAGE);
        } catch (ParameterException parameterEceptione) {
            Map<String, String> errorFileMap = parameterEceptione.getErrorFileMap();
            modelAndView.addObject(Oes.ERRORMAP, errorFileMap);
            modelAndView.setView(LOGIN_PAGE);
        }
        return modelAndView;
    }
}