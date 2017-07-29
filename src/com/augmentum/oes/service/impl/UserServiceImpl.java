package com.augmentum.oes.service.impl;

import org.apache.log4j.Logger;

import com.augmentum.oes.dao.UserDao;
import com.augmentum.oes.exception.ParameterException;
import com.augmentum.oes.exception.ServiceException;
import com.augmentum.oes.model.User;
import com.augmentum.oes.service.UserService;

public class UserServiceImpl implements UserService {
    private final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private UserDao userDao;

    @Override
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String userName, String password) throws ParameterException, ServiceException {
        ParameterException parameterEception = new ParameterException();

        if (userName == null || userName.equals("")) {
            parameterEception.adderrorFile("userName", "username in requirzed!");
        }

        if (password == null || password.equals("")) {
            parameterEception.adderrorFile("password", "password in requirzed!");
        }

        if (parameterEception.isErrorField()) {
            throw parameterEception;
        }

        User user = userDao.getUser(userName);

        if (user == null) {
            throw new ServiceException(1000, "login is wrong!");
        }

        if (!password.equals(user.getPassword())) {
            throw new ServiceException(1001, "login is wrong!");
        }

        return user;
    }
}
