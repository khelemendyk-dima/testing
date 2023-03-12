package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.util.List;

import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is SearchQuestionAction class. Accessible by admin. Allows to find question.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class SearchQuestionAction implements Action {
    private static final Logger logger = LogManager.getLogger(SearchQuestionAction.class);
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * @param appContext contains QuestionService and AnswerService instances to use in action
     */
    public SearchQuestionAction(AppContext appContext) {
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
    }

    /**
     * Obtains required path and sets question with its answers to request if it was found
     * @param request to get question id and put question with answers in request
     * @return edit question page if question was found, else - error page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String questionId = request.getParameter(QUESTION_ID);

        try {
            QuestionDTO question = questionService.getById(questionId);
            List<AnswerDTO> answers = answerService.getAllByQuestionId(questionId);

            request.setAttribute(QUESTION, question);
            request.setAttribute(ANSWERS, answers);
            request.setAttribute(NUMBER_OF_ANSWERS, answers.size());
            logger.info(String.format("Question with id = %s was successfully found", questionId));

            return EDIT_QUESTION_PAGE;
        } catch (ServiceException e) {
            logger.error(String.format("Couldn't find question with id = %s", questionId));
            return ERROR_PAGE;
        }
    }
}
