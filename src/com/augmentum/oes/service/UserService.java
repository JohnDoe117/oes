package com.augmentum.oes.service;

import com.augmentum.oes.dao.UserDao;
import com.augmentum.oes.exception.ParameterException;
import com.augmentum.oes.exception.ServiceException;
import com.augmentum.oes.model.User;

public interface UserService {

    public void setUserDao(UserDao userDao);

    public User login(String userName, String password) throws ParameterException, ServiceException;
}
