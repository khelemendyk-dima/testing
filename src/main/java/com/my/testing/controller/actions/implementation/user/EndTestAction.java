package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.util.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.END_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is EndTestAction class. Accessible by any logged user. Allows to end test
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class EndTestAction implements Action {
    private static final Logger logger = LogManager.getLogger(EndTestAction.class);
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final TestResultService testResultService;

    /**
     * @param appContext contains TestService, QuestionService, AnswerService and TestResultService to use in action
     */
    public EndTestAction(AppContext appContext) {
        testService = appContext.getTestService();
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
        testResultService = appContext.getTestResultService();
    }

    /**
     * Checks method and calls required implementation
     * @param request to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller* @param response passed by controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request. Executes if only error happens.
     * @param request to get number of correct answers, user's score, test attributes from session and put it in request
     * @return view result page
     */
    private String executeGet(HttpServletRequest request) {
        transferIntFromSessionToRequest(request, NUMBER_OF_CORRECT_ANSWERS);
        transferFloatFromSessionToRequest(request, SCORE);
        transferTestDTOFromSessionToRequest(request);
        request.getSession().removeAttribute(FINISH_TEST_TIME);

        return (String)request.getSession().getAttribute(CURRENT_PATH);
    }

    /**
     * Called from doPost method in front-controller. Gets test id, logged user and user's answers from request.
     * Then gets from database answers and asserts them with user's answers. Next calculates user's score.
     * @param request to get test id, logged user and user's answers and set some attributes to session
     * @return view result page. If something go wrong - error page
     */
    private String executePost(HttpServletRequest request) {
        String path = VIEW_RESULT_PAGE;

        String testId = request.getParameter(TEST_ID);
        try {
            TestDTO testDTO = testService.getById(testId);

            int numberOfCorrectAnswers = getNumberOfCorrectAnswers(request, testId);
            float userScore = (numberOfCorrectAnswers * 100) / (float)testDTO.getNumberOfQueries();

            UserDTO loggedUser = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
            long userId = loggedUser.getId();

            TestResultDTO testResult = createTestResultDTO(testId, userId, userScore);
            testResultService.add(testResult);

            request.getSession().setAttribute(TEST, testDTO);
            request.getSession().setAttribute(NUMBER_OF_CORRECT_ANSWERS, numberOfCorrectAnswers);
            request.getSession().setAttribute(SCORE, userScore);

        } catch (ServiceException e) {
            logger.error(String.format("Couldn't end test with id = %s because of %s", testId, e.getMessage()));
            path = ERROR_PAGE;
        }

        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(END_TEST_ACTION);
    }

    /**
     * Calculates number of correct answers by asserting user's answers with answers from database
     * @param request to get user's answers
     * @param testId to get answers from database
     * @return number of correct answers
     */
    private int getNumberOfCorrectAnswers(HttpServletRequest request, String testId) throws ServiceException {
        int numberOfCorrectAnswers = 0;
        int answerCounter = 0;
        List<QuestionDTO> questionDTOS = questionService.getAllByTestId(testId);

        for (QuestionDTO questionDTO : questionDTOS) {
            List<AnswerDTO> answerDTOS = answerService.getAllByQuestionId(String.valueOf(questionDTO.getId()));

            boolean isCorrectAnswer = true;
            for (AnswerDTO answerDTO : answerDTOS) {
                boolean userAnswer = Objects.equals(request.getParameter(CORRECT + (++answerCounter)), "true");
                if (userAnswer != answerDTO.isCorrect()) {
                    isCorrectAnswer = false;
                }
            }

            if (isCorrectAnswer) {
                numberOfCorrectAnswers++;
            }
        }

        return numberOfCorrectAnswers;
    }

    private TestResultDTO createTestResultDTO(String testId, long userId, float userScore) {
        return TestResultDTO.builder()
                .testId(Long.parseLong(testId))
                .userId(userId)
                .result(userScore)
                .build();
    }
}
