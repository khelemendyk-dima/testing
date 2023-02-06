package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.AnswerService;
import com.my.testing.model.services.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.DELETE_QUESTION_ACTION;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class DeleteQuestionAction implements Action {
    private static final Logger logger = LogManager.getLogger(DeleteQuestionAction.class);
    private final QuestionService questionService;
    private final AnswerService answerService;

    public DeleteQuestionAction(AppContext appContext) {
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) {
        String path = "controller?action=search-test&id=" + request.getParameter(TEST_ID);
        String questionId = request.getParameter(QUESTION_ID);

        try {
            answerService.deleteAllByQuestionId(questionId);
            questionService.delete(questionId);
        } catch (ServiceException e) {
            logger.error("Couldn't delete question - no such question");
        }

        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(DELETE_QUESTION_ACTION);
    }
}
