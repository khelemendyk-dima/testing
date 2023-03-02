package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class SolveTestAction implements Action {
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public SolveTestAction(AppContext appContext) {
        testService = appContext.getTestService();
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String path = SOLVE_TEST_PAGE;
        try {
            String testId = request.getParameter(ID);
            TestDTO testDTO = testService.getById(testId);
            List<QuestionDTO> questionDTOS = questionService.getAllByTestId(testId);
            List<AnswerDTO> answerDTOS = new ArrayList<>();

            for (QuestionDTO questionDTO : questionDTOS) {
                answerDTOS.addAll(answerService.getAllByQuestionId(String.valueOf(questionDTO.getId())));
            }

            request.setAttribute(TEST, testDTO);
            request.setAttribute(QUESTIONS, questionDTOS);
            request.setAttribute(ANSWERS, answerDTOS);
        } catch (ServiceException e) {
            path = ERROR_PAGE;
        }

        return path;
    }
}