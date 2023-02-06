package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class SearchQuestionAction implements Action {
    private final QuestionService questionService;
    private final AnswerService answerService;

    public SearchQuestionAction(AppContext appContext) {
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String path = EDIT_QUESTION_PAGE;
        String questionId = request.getParameter(QUESTION_ID);

        try {
            QuestionDTO question = questionService.getById(questionId);
            List<AnswerDTO> answers = answerService.getAllByQuestionId(questionId);

            request.setAttribute(QUESTION, question);
            request.setAttribute(ANSWERS, answers);
            request.setAttribute(NUMBER_OF_ANSWERS, answers.size());
        } catch (ServiceException e) {
            path = ERROR_PAGE;
        }

        return path;
    }
}
