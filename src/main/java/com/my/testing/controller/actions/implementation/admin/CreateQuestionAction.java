package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.util.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.*;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is CreateQuestionAction class. Accessible by admin. Allows to create new question. Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class CreateQuestionAction implements Action {
    private static final Logger logger = LogManager.getLogger(CreateQuestionAction.class);
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * @param appContext contains QuestionService and AnswerService instances to use in action
     */
    public CreateQuestionAction(AppContext appContext) {
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    /**
     * Checks method and calls required implementation
     *
     * @param request to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request. Executes if only error happens.
     *
     * @param request to get error, question, answers attributes from session and put it in request
     * @return create question page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, ERROR);
        transferQuestionDTOFromSessionToRequest(request);
        transferAnswersTextDTOFromSessionToRequest(request);

        return CREATE_QUESTION_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Gets question and answers from request. Then validates them.
     * And adding question with answers to database
     * @param request to get question and answers and set some attributes to session
     * @return view test page or redirects to create question action if something go wrong
     */
    private String executePost(HttpServletRequest request) {
        QuestionDTO question = getQuestionDTO(request);

        List<String> answersText = new ArrayList<>();
        List<Boolean> answersValue = new ArrayList<>();

        getAnswersFromRequest(request, answersText, answersValue);

        try {
            validateQuestionAndAnswers(question, answersText, answersValue);

            questionService.add(question);
            List<AnswerDTO> answers = getAnswersDTO(answersText, answersValue, question.getId());
            for (AnswerDTO answer : answers) {
                answerService.add(answer);
            }
            logger.info(String.format("Question %s was created", question.getText()));

            return getActionToRedirect(SEARCH_TEST_ACTION, ID, request.getParameter(TEST_ID));
        } catch (ServiceException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            request.getSession().setAttribute(ANSWERS_TEXT, answersText);
            request.getSession().setAttribute(QUESTION, question);
            logger.info(String.format("Couldn't creat question %s because of %s", question.getText(),  e.getMessage()));

            return getActionToRedirect(CREATE_QUESTION_ACTION);
        }
    }

    private QuestionDTO getQuestionDTO(HttpServletRequest request) {
        return QuestionDTO.builder()
                .text(request.getParameter(QUESTION_TEXT))
                .testId(Long.parseLong(request.getParameter(TEST_ID)))
                .build();
    }
}
