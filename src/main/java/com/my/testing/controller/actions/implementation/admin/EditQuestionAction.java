package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.*;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.exceptions.constants.Message.*;
import static com.my.testing.utils.ValidatorUtil.*;

public class EditQuestionAction implements Action {
    private static final int MAX_NUMBER_OF_ANSWERS = 8;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public EditQuestionAction(AppContext appContext) {
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, ERROR);
        transferQuestionDTOFromSessionToRequest(request);
        transferAnswersDTOFromSessionToRequest(request);

        return getPath(request);
    }

    private String executePost(HttpServletRequest request) {
        String path = CONTROLLER_PAGE + "?action=" + SEARCH_TEST_ACTION + "&id=" + request.getParameter(TEST_ID);

        QuestionDTO question = getQuestionDTO(request);
        List<AnswerDTO> answers = new ArrayList<>();

        boolean isOneAnswerCorrect = false;
        for (int i = 1; i <= MAX_NUMBER_OF_ANSWERS; i++) {
            String answerText = request.getParameter(ANSWER + i);
            boolean isCorrectAnswer = Objects.equals(request.getParameter(CORRECT + i), "true");

            if (answerText != null && !answerText.isBlank()) {
                if (isCorrectAnswer)
                    isOneAnswerCorrect = true;
                answers.add(getAnswerDTO(answerText, isCorrectAnswer, question.getId()));
            }
        }

        try {
            if (!isOneAnswerCorrect) {
                throw new IncorrectFormatException(NO_CORRECT_ANSWER);
            } else if (answers.size() < 2) {
                throw new IncorrectFormatException(FEW_ANSWERS);
            } else {
                validateTexts(question, answers);

                questionService.update(question);
                answerService.deleteAllByQuestionId(String.valueOf(question.getId()));

                for (AnswerDTO answer : answers) {
                    answerService.add(answer);
                }
            }
        } catch (ServiceException e) {
            path = EDIT_QUESTION_PAGE;

            request.getSession().setAttribute(ERROR, e.getMessage());
            request.getSession().setAttribute(QUESTION, question);
            request.getSession().setAttribute(ANSWERS, answers);
        }

        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(EDIT_QUESTION_ACTION);
    }

    private QuestionDTO getQuestionDTO(HttpServletRequest request) {
        return QuestionDTO.builder()
                .id(Long.parseLong(request.getParameter(QUESTION_ID)))
                .text(request.getParameter(QUESTION_TEXT))
                .testId(Long.parseLong(request.getParameter(TEST_ID)))
                .build();
    }

    private AnswerDTO getAnswerDTO(String answerText, boolean isCorrectAnswer, long questionId) {
        return AnswerDTO.builder()
                .text(answerText)
                .isCorrect(isCorrectAnswer)
                .questionId(questionId)
                .build();
    }

    private void validateTexts(QuestionDTO question, List<AnswerDTO> answers) throws IncorrectFormatException {
        validateQuestionText(question.getText());
        for (AnswerDTO answer : answers) {
            validateAnswerText(answer.getText());
        }
    }
}