package com.my.testing.controller.actions;

import com.my.testing.dto.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

import static com.my.testing.controller.actions.constants.Pages.CONTROLLER_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class ActionUtil {

    private ActionUtil() {}

    public static boolean isPostMethod(HttpServletRequest request) {
        return request.getMethod().equals("POST");
    }

    public static String getPath(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(CURRENT_PATH);
    }

    public static void transferStringFromSessionToRequest(HttpServletRequest request, String attributeName) {
        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    public static void transferUserDTOFromSessionToRequest(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute(USER);
        if (user != null) {
            request.setAttribute(USER, user);
            request.getSession().removeAttribute(USER);
        }
    }

    public static void transferTestDTOFromSessionToRequest(HttpServletRequest request) {
        TestDTO test = (TestDTO) request.getSession().getAttribute(TEST);
        if (test != null) {
            request.setAttribute(TEST, test);
            request.getSession().removeAttribute(TEST);
        }
    }

    public static void transferQuestionDTOFromSessionToRequest(HttpServletRequest request) {
        QuestionDTO question = (QuestionDTO) request.getSession().getAttribute(QUESTION);
        if (question != null) {
            request.setAttribute(QUESTION, question);
            request.getSession().removeAttribute(QUESTION);
        }
    }

    @SuppressWarnings("unchecked")
    public static void transferAnswersTextDTOFromSessionToRequest(HttpServletRequest request) {
            List<String> answerText = (List<String>) request.getSession().getAttribute(ANSWERS_TEXT);
            if (answerText != null) {
                request.setAttribute(ANSWERS_TEXT, answerText);
                request.setAttribute(NUMBER_OF_ANSWERS, answerText.size());

                request.getSession().removeAttribute(ANSWERS_TEXT);
            }
    }

    @SuppressWarnings("unchecked")
    public static void transferAnswersDTOFromSessionToRequest(HttpServletRequest request) {
        List<AnswerDTO> answers = (List<AnswerDTO>) request.getSession().getAttribute(ANSWERS);
        if (answers != null) {
            request.setAttribute(ANSWERS, answers);
            request.setAttribute(NUMBER_OF_ANSWERS, answers.size());

            request.getSession().removeAttribute(ANSWERS);
        }
    }

    public static String getActionToRedirect(String action, String ... parameters) {
        String base = CONTROLLER_PAGE + "?" + ACTION + "=" + action;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        for (int i = 0; i < parameters.length; i += 2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }

        return base + (parameters.length > 0 ? stringJoiner : "");
    }
}
