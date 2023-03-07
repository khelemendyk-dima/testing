package com.my.testing.model.services;

import com.my.testing.model.dao.DAOFactory;
import com.my.testing.model.services.implementation.*;
import lombok.Getter;

/**
 * Service factory that provides concrete implementation of UserService, TestService,
 * QuestionService, AnswerService and TestResultService
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Getter
public class ServiceFactory {

    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final TestResultService testResultService;

    private ServiceFactory(DAOFactory daoFactory) {
        userService = new UserServiceImpl(daoFactory.getUserDAO());
        testService = new TestServiceImpl(daoFactory.getTestDAO());
        questionService = new QuestionServiceImpl(daoFactory.getQuestionDAO());
        answerService = new AnswerServiceImpl(daoFactory.getAnswerDAO());
        testResultService = new TestResultServiceImpl(daoFactory.getTestResultDAO());
    }

    /**
     * Obtains instance of ServiceFactory to get concrete Service
     * @param daoFactory pass concrete DAOFactory instance to get access to DAO
     * @return ServiceFactory instance
     */
    public static ServiceFactory getInstance(DAOFactory daoFactory) {
        return new ServiceFactory(daoFactory);
    }
}
