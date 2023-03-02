package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.QuestionDTO;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.*;

import java.util.List;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.DELETE_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class DeleteTestAction implements Action {
    private static final Logger logger = LogManager.getLogger(DeleteTestAction.class);
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public DeleteTestAction(AppContext appContext) {
        testService = appContext.getTestService();
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) {
        String path = "controller?action=view-tests";
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

        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(DELETE_TEST_ACTION);
    }
}
