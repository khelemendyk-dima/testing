package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.QuestionDTO;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.util.List;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.VIEW_TESTS_ACTION;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is DeleteTestAction class. Accessible by admin. Allows to delete test.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class DeleteTestAction implements Action {
    private static final Logger logger = LogManager.getLogger(DeleteTestAction.class);
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * @param appContext contains TestService, QuestionService and AnswerService instances to use in action
     */
    public DeleteTestAction(AppContext appContext) {
        testService = appContext.getTestService();
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    /**
     * Gets test id from request and deletes test with questions and answers from database
     * @param request to get test id
     * @return path to redirect
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String testId = request.getParameter(TEST_ID);

        try {
            List<QuestionDTO> questions = questionService.getAllByTestId(testId);
            for (QuestionDTO question : questions) {
                String questionId = String.valueOf(question.getId());
                answerService.deleteAllByQuestionId(questionId);
                questionService.delete(questionId);
            }
            testService.delete(testId);
        } catch (ServiceException e) {
            logger.error("Couldn't delete test - no such test");
        }

        return getActionToRedirect(VIEW_TESTS_ACTION);
    }
}
