package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.SEARCH_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is DeleteQuestionAction class. Accessible by admin. Allows to delete question.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class DeleteQuestionAction implements Action {
    private static final Logger logger = LogManager.getLogger(DeleteQuestionAction.class);
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * @param appContext contains QuestionService, AnswerService instances to use in action
     */
    public DeleteQuestionAction(AppContext appContext) {
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    /**
     * Gets question id from request and deletes them with answers via services
     * @param request to get question id
     * @return path to redirect
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String questionId = request.getParameter(QUESTION_ID);

        try {
            answerService.deleteAllByQuestionId(questionId);
            questionService.delete(questionId);
        } catch (ServiceException e) {
            logger.error("Couldn't delete question - no such question");
        }

        return getActionToRedirect(SEARCH_TEST_ACTION, ID, request.getParameter(TEST_ID));
    }
}
