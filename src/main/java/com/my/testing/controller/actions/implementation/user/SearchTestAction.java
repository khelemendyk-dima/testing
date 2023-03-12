package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.util.*;

import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is SearchTestAction class. Accessible by any logged user. Allows to search test from database by test id
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class SearchTestAction implements Action {
    private static final Logger logger = LogManager.getLogger(SearchTestAction.class);
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * @param appContext contains TestService, QuestionService and AnswerService instances to use in action
     */
    public SearchTestAction(AppContext appContext) {
        testService = appContext.getTestService();
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    /**
     * Obtains required path and sets test to request if it was found
     * @param request to get test and put it in request
     * @return view test page if it was found, else - error page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            String testId = request.getParameter(ID);
            TestDTO testDTO = testService.getById(testId);
            List<QuestionDTO> questionDTOS = questionService.getAllByTestId(testId);
            List<AnswerDTO> answerDTOS = new ArrayList<>();

            for (QuestionDTO questionDTO : questionDTOS) {
                answerDTOS.addAll(answerService.getAllByQuestionId(String.valueOf(questionDTO.getId())));
            }

            request.setAttribute(TEST, testDTO);
            request.setAttribute(QUESTIONS, questionDTOS);
            request.setAttribute(ANSWERS, answerDTOS);

            return VIEW_TEST_PAGE;
        } catch (NoSuchTestException e) {
            logger.info(String.format("Couldn't find test because of %s", e.getMessage()));
            return ERROR_PAGE;
        }
    }
}
