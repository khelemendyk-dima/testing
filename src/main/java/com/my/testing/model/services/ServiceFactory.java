package com.my.testing.model.services;

import com.my.testing.model.dao.DAOFactory;
import com.my.testing.model.services.implementation.AnswerServiceImpl;
import com.my.testing.model.services.implementation.QuestionServiceImpl;
import com.my.testing.model.services.implementation.TestServiceImpl;
import com.my.testing.model.services.implementation.UserServiceImpl;

public class ServiceFactory {

    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    private ServiceFactory() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        userService = new UserServiceImpl(daoFactory.getUserDAO());
        testService = new TestServiceImpl(daoFactory.getTestDAO());
        questionService = new QuestionServiceImpl(daoFactory.getQuestionDAO());
        answerService = new AnswerServiceImpl(daoFactory.getAnswerDAO());
    }

    public static ServiceFactory getInstance() {
        return new ServiceFactory();
    }

    public UserService getUserService() {
        return userService;
    }

    public TestService getTestService() {
        return testService;
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    public AnswerService getAnswerService() {
        return answerService;
    }
}
