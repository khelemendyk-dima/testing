package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class SearchTestAction implements Action {
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public SearchTestAction(AppContext appContext) {
        testService = appContext.getTestService();
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String path = VIEW_TEST_PAGE;
        String testId = request.getParameter(ID);
        try {
            TestDTO testDTO = testService.getById(testId);
            List<QuestionDTO> questionDTOS = questionService.getAllByTestId(testId);
            List<AnswerDTO> answerDTOS = new ArrayList<>();

            for (QuestionDTO questionDTO : questionDTOS) {
                answerDTOS.addAll(answerService.getAllByQuestionId(String.valueOf(questionDTO.getId())));
            }

            request.setAttribute(TEST, testDTO);
            request.setAttribute(QUESTIONS, questionDTOS);
            request.setAttribute(ANSWERS, answerDTOS);
        } catch (NoSuchTestException e) {
            path = ERROR_PAGE;
        }

        return path;
    }
}
