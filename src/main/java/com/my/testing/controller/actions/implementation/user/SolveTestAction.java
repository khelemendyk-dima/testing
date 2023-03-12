package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.util.*;

import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is SolveTestAction class. Accessible by any logged user. Allows to solve test
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class SolveTestAction implements Action {
    private static final Logger logger = LogManager.getLogger(SolveTestAction.class);
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * @param appContext contains TestService, QuestionService and AnswerService to use in action
     */
    public SolveTestAction(AppContext appContext) {
        testService = appContext.getTestService();
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    /**
     * Obtains test with questions and answers and sets them to request
     * @param request to get test id
     * @return solve test page if test was found, else - error page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String testId = request.getParameter(ID);
        try {
            TestDTO testDTO = testService.getById(testId);
            List<QuestionDTO> questionDTOS = questionService.getAllByTestId(testId);
            List<AnswerDTO> answerDTOS = new ArrayList<>();

            for (QuestionDTO questionDTO : questionDTOS) {
                answerDTOS.addAll(answerService.getAllByQuestionId(String.valueOf(questionDTO.getId())));
            }

            request.setAttribute(TEST, testDTO);
            request.setAttribute(QUESTIONS, questionDTOS);
            request.setAttribute(ANSWERS, answerDTOS);

            return SOLVE_TEST_PAGE;
        } catch (ServiceException e) {
            logger.error(String.format("Couldn't find test with id = %s because of %s", testId, e.getMessage()));
            return ERROR_PAGE;
        }
    }
}