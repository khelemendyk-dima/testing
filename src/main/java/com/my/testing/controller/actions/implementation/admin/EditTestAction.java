package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.TestService;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.*;
import static com.my.testing.controller.actions.constants.Pages.EDIT_TEST_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.exceptions.constants.Message.ENTER_CORRECT_NAME;

/**
 * This is EditTestAction class. Accessible by admin. Allows to edit test's name, subject, difficulty and duration.
 * Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class EditTestAction implements Action {
    private static final Logger logger = LogManager.getLogger(EditTestAction.class);
    private final TestService testService;

    /**
     * @param appContext contains TestService instance to use in action
     */
    public EditTestAction(AppContext appContext) {
        testService = appContext.getTestService();
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
     * @param request to get test and error attributes from session and put it in request
     * @return edit test page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, ERROR);
        transferTestDTOFromSessionToRequest(request);

        return EDIT_TEST_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Gets test from request.
     * And updating test in database
     * @param request to get test and set some attributes to session
     * @return view test page or redirects to edit test action if something go wrong
     */
    private String executePost(HttpServletRequest request) {
        TestDTO test = getTestDTO(request);

        try {
            testService.update(test);
            logger.info(String.format("Test with id = %s was successfully changed", test.getId()));

            return getActionToRedirect(SEARCH_TEST_ACTION, ID, String.valueOf(test.getId()));
        } catch (ServiceException e) {
            request.getSession().setAttribute(ERROR, ENTER_CORRECT_NAME);
            request.getSession().setAttribute(TEST, test);
            logger.error(String.format("Couldn't change test with id = %s because of %s", test.getId(), e.getMessage()));

            return getActionToRedirect(EDIT_TEST_ACTION);
        }
    }

    private TestDTO getTestDTO(HttpServletRequest request) {
        return TestDTO.builder()
                .id(Long.parseLong(request.getParameter(ID)))
                .name(request.getParameter(NAME))
                .subject(request.getParameter(SUBJECT))
                .difficulty(request.getParameter(DIFFICULTY))
                .duration(Integer.parseInt(request.getParameter(DURATION)))
                .build();
    }
}
