package com.my.testing.controller.actions;

import com.my.testing.dto.*;
import com.my.testing.exceptions.IncorrectFormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

import java.util.*;

import static com.my.testing.controller.actions.constants.Pages.CONTROLLER_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.exceptions.constants.Message.*;
import static com.my.testing.utils.ValidatorUtil.*;

/**
 * ActionUtil class. Contains utils methods to user in actions.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActionUtil {
    private static final int MAX_NUMBER_OF_ANSWERS = 8;

    /**
     * Checks if method is POST method
     * @param request passed by action
     * @return true if POST method
     */
    public static boolean isPostMethod(HttpServletRequest request) {
        return request.getMethod().equals("POST");
    }

    /**
     * Transfers session's attributes to request. Then remove them from session
     * @param request passed by action
     * @param attributeName name of attribute to transfer from session to request
     */
    public static void transferStringFromSessionToRequest(HttpServletRequest request, String attributeName) {
        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    /**
     * Transfers session's attributes to request. Then remove them from session
     * @param request passed by action
     * @param attributeName name of attribute to transfer from session to request
     */
    public static void transferIntFromSessionToRequest(HttpServletRequest request, String attributeName) {
        Integer attributeValue = (Integer) request.getSession().getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    /**
     * Transfers session's attributes to request. Then remove them from session
     * @param request passed by action
     * @param attributeName name of attribute to transfer from session to request
     */
    public static void transferFloatFromSessionToRequest(HttpServletRequest request, String attributeName) {
        Float attributeValue = (Float) request.getSession().getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    /**
     * Transfers session's attributes to request. Then remove them from session
     * @param request passed by action
     */
    public static void transferUserDTOFromSessionToRequest(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute(USER);
        if (user != null) {
            request.setAttribute(USER, user);
            request.getSession().removeAttribute(USER);
        }
    }

    /**
     * Transfers session's attributes to request. Then remove them from session
     * @param request passed by action
     */
    public static void transferTestDTOFromSessionToRequest(HttpServletRequest request) {
        TestDTO test = (TestDTO) request.getSession().getAttribute(TEST);
        if (test != null) {
            request.setAttribute(TEST, test);
            request.getSession().removeAttribute(TEST);
        }
    }

    /**
     * Transfers session's attributes to request. Then remove them from session
     * @param request passed by action
     */
    public static void transferQuestionDTOFromSessionToRequest(HttpServletRequest request) {
        QuestionDTO question = (QuestionDTO) request.getSession().getAttribute(QUESTION);
        if (question != null) {
            request.setAttribute(QUESTION, question);
            request.getSession().removeAttribute(QUESTION);
        }
    }

    /**
     * Transfers session's attributes to request. Then remove them from session
     * @param request passed by action
     */
    @SuppressWarnings("unchecked")
    public static void transferAnswersTextDTOFromSessionToRequest(HttpServletRequest request) {
            List<String> answerText = (List<String>) request.getSession().getAttribute(ANSWERS_TEXT);
            if (answerText != null) {
                request.setAttribute(ANSWERS_TEXT, answerText);
                request.setAttribute(NUMBER_OF_ANSWERS, answerText.size());

                request.getSession().removeAttribute(ANSWERS_TEXT);
            }
    }

    /**
     * Transfers session's attributes to request. Then remove them from session
     * @param request passed by action
     */
    @SuppressWarnings("unchecked")
    public static void transferAnswersDTOFromSessionToRequest(HttpServletRequest request) {
        List<AnswerDTO> answers = (List<AnswerDTO>) request.getSession().getAttribute(ANSWERS);
        if (answers != null) {
            request.setAttribute(ANSWERS, answers);
            request.setAttribute(NUMBER_OF_ANSWERS, answers.size());

            request.getSession().removeAttribute(ANSWERS);
        }
    }

    /**
     * Gets answers text and value from session and sets them to lists
     * @param request to get answers text and value
     * @param answersText list of answers text
     * @param answersValue list of answers value
     */
    public static void getAnswersFromRequest(HttpServletRequest request, List<String> answersText, List<Boolean> answersValue) {
        for (int i = 1; i <= MAX_NUMBER_OF_ANSWERS; i++) {
            String answerText = request.getParameter(ANSWER + i);
            boolean isCorrectAnswer = Objects.equals(request.getParameter(CORRECT + i), "true");
            if (answerText != null && !answerText.isBlank()) {
                answersText.add(answerText);
                answersValue.add(isCorrectAnswer);
            }
        }
    }

    /**
     * Validates question, answers text and answers value. Question must contain one correct answer and at least
     * two answers
     * @param question to validate question text
     * @param answersText to validate answer text and check number of answers
     * @param answersValue to check if at least one answer correct
     * @throws IncorrectFormatException if validation fails
     */
    public static void validateQuestionAndAnswers(QuestionDTO question, List<String> answersText, List<Boolean> answersValue) throws IncorrectFormatException {
        if (!answersValue.contains(true)) {
            throw new IncorrectFormatException(NO_CORRECT_ANSWER);
        } else if (answersText.size() < 2) {
            throw new IncorrectFormatException(FEW_ANSWERS);
        }

        validateQuestionText(question.getText());
        for (String s : answersText) {
            validateAnswerText(s);
        }
    }

    /**
     * Creates list of AnswerDTOs from answers text and answers value
     * @param answersText to create AnswerDTO entity
     * @param answersValue to create AnswerDTO entity
     * @param questionId to set question id for AnswerDTO entity
     * @return list of AnswerDTOs
     */
    public static List<AnswerDTO> getAnswersDTO(List<String> answersText, List<Boolean> answersValue, long questionId) {
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

    /**
     * Creates path to another Action
     * @param action Action to be sent
     * @param parameters required parameters
     * @return path
     */
    public static String getActionToRedirect(String action, String ... parameters) {
        String base = CONTROLLER_PAGE + "?" + ACTION + "=" + action;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "").setEmptyValue("");
        for (int i = 0; i < parameters.length; i += 2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }

        return base + stringJoiner;
    }
}
