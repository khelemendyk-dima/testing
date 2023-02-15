package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.*;

import java.util.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.END_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class EndTestAction implements Action {
    private static final Logger logger = LogManager.getLogger(EndTestAction.class);
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final TestResultService testResultService;

    public EndTestAction(AppContext appContext) {
        testService = appContext.getTestService();
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
        testResultService = appContext.getTestResultService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferIntFromSessionToRequest(request, NUMBER_OF_CORRECT_ANSWERS);
        transferFloatFromSessionToRequest(request, SCORE);
        transferTestDTOFromSessionToRequest(request);
        request.getSession().removeAttribute(FINISH_TEST_TIME);
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) {
        String path = VIEW_RESULT_PAGE;
        
        try {
            String testId = request.getParameter(TEST_ID);
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
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }

        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(END_TEST_ACTION);
    }

    private TestResultDTO createTestResultDTO(String testId, long userId, float userScore) {
        return TestResultDTO.builder()
                .testId(Long.parseLong(testId))
                .userId(userId)
                .result(userScore)
                .build();
    }

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
}
