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
 * This is EditQuestionAction class. Accessible by admin. Allows to edit question with answers.
 * Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class EditQuestionAction implements Action {
    private static final Logger logger = LogManager.getLogger(EditQuestionAction.class);
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * @param appContext contains QuestionService and AnswerService to use in action
     */
    public EditQuestionAction(AppContext appContext) {
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
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
     * @param request to get question, answers and error attributes from session and put it in request
     * @return edit question page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, ERROR);
        transferQuestionDTOFromSessionToRequest(request);
        transferAnswersDTOFromSessionToRequest(request);

        return EDIT_QUESTION_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Gets question and answers from request. Then validates them.
     * And updating question with answers in database
     * @param request to get question and answers and set some attributes to session
     * @return view test page or redirects to edit question action if something go wrong
     */
    private String executePost(HttpServletRequest request) {
        QuestionDTO question = getQuestionDTO(request);

        List<String> answersText = new ArrayList<>();
        List<Boolean> answersValue = new ArrayList<>();

        getAnswersFromRequest(request, answersText, answersValue);
        List<AnswerDTO> answers = getAnswersDTO(answersText, answersValue, question.getId());

        try {
            validateQuestionAndAnswers(question, answersText, answersValue);

            questionService.update(question);
            answerService.deleteAllByQuestionId(String.valueOf(question.getId()));

            for (AnswerDTO answer : answers) {
                answerService.add(answer);
            }
            logger.info(String.format("Question with id = %s was successfully changed", question.getId()));

            return getActionToRedirect(SEARCH_TEST_ACTION, ID, request.getParameter(TEST_ID));
        } catch (ServiceException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            request.getSession().setAttribute(QUESTION, question);
            request.getSession().setAttribute(ANSWERS, answers);
            logger.error(String.format("Couldn't change question with id = %s because of %s", question.getId(), e.getMessage()));

            return getActionToRedirect(EDIT_QUESTION_ACTION);
        }
    }

    private QuestionDTO getQuestionDTO(HttpServletRequest request) {
        return QuestionDTO.builder()
                .id(Long.parseLong(request.getParameter(QUESTION_ID)))
                .text(request.getParameter(QUESTION_TEXT))
                .testId(Long.parseLong(request.getParameter(TEST_ID)))
                .build();
    }
}