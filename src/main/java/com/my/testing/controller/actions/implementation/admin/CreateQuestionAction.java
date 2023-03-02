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

public class CreateQuestionAction implements Action {
    private static final int MAX_NUMBER_OF_ANSWERS = 8;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public CreateQuestionAction(AppContext appContext) {
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
        transferAnswersTextDTOFromSessionToRequest(request);

        return getPath(request);
    }

    private String executePost(HttpServletRequest request) {
        String path = "controller?action=search-test&id=" + request.getParameter(TEST_ID);

        QuestionDTO question = getQuestionDTO(request);

        List<String> answersText = new ArrayList<>();
        List<Boolean> answersValue = new ArrayList<>();

        for (int i = 1; i <= MAX_NUMBER_OF_ANSWERS; i++) {
            String answerText = request.getParameter(ANSWER + i);
            boolean isCorrectAnswer = Objects.equals(request.getParameter(CORRECT + i), "true");
            if (answerText != null && !answerText.isBlank()) {
                answersText.add(answerText);
                answersValue.add(isCorrectAnswer);
            }
        }

        try {
            if (!answersValue.contains(true)) {
                throw new IncorrectFormatException(NO_CORRECT_ANSWER);
            } else if (answersText.size() < 2) {
                throw new IncorrectFormatException(FEW_ANSWERS);
            } else {
                validateTexts(question, answersText);

                questionService.add(question);

                List<AnswerDTO> answers = getAnswersDTO(answersText, answersValue, question.getId());
                for (AnswerDTO answer : answers) {
                    answerService.add(answer);
                }
            }
        } catch (ServiceException e) {
            path = CREATE_QUESTION_PAGE;

            request.getSession().setAttribute(ERROR, e.getMessage());
            request.getSession().setAttribute(ANSWERS_TEXT, answersText);
            request.getSession().setAttribute(QUESTION, question);
        }

        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(CREATE_QUESTION_ACTION);
    }

    private QuestionDTO getQuestionDTO(HttpServletRequest request) {
        return QuestionDTO.builder()
                .text(request.getParameter(QUESTION_TEXT))
                .testId(Long.parseLong(request.getParameter(TEST_ID)))
                .build();
    }

    private List<AnswerDTO> getAnswersDTO(List<String> answersText, List<Boolean> answersValue, long questionId) {
        List<AnswerDTO> answers = new ArrayList<>();

        for (int i = 0; i < answersText.size(); i++) {
            answers.add(AnswerDTO.builder()
                    .text(answersText.get(i))
                    .isCorrect(answersValue.get(i))
                    .questionId(questionId)
                    .build());
        }

        return answers;
    }

    private void validateTexts(QuestionDTO question, List<String> answersText) throws IncorrectFormatException {
        validateQuestionText(question.getText());
        for (String s : answersText) {
            validateAnswerText(s);
        }
    }
}
