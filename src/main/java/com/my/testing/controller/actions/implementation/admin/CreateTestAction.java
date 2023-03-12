package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.TestService;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.*;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is CreateTestAction class. Accessible by admin. Allows to create new test. Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class CreateTestAction implements Action {
    private static final Logger logger = LogManager.getLogger(CreateTestAction.class);
    private final TestService testService;

    /**
     * @param appContext contains TestService instance to use in action
     */
    public CreateTestAction(AppContext appContext) {
        testService = appContext.getTestService();
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
     * @param request to get error and test attributes from session and put it in request
     * @return create question page
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, ERROR);
        transferTestDTOFromSessionToRequest(request);

        return CREATE_TEST_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Gets test from request and tries to add it via service
     *
     * @param request to get test
     * @return view test page or redirects to create test action if something go wrong
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        TestDTO test = getTestDTO(request);
        try {
            testService.add(test);
            logger.info(String.format("Test %s was created", test.getName()));
            return getActionToRedirect(SEARCH_TEST_ACTION, ID, String.valueOf(test.getId()));
        } catch (IncorrectFormatException e) {
            logger.info(String.format("Couldn't creat test %s because of %s", test.getName(),  e.getMessage()));
            request.getSession().setAttribute(ERROR, e.getMessage());
            request.getSession().setAttribute(TEST, test);
            return getActionToRedirect(CREATE_TEST_ACTION);
        }
    }

    private TestDTO getTestDTO(HttpServletRequest request) {
        return TestDTO.builder()
                .name(request.getParameter(NAME))
                .subject(request.getParameter(SUBJECT))
                .difficulty(request.getParameter(DIFFICULTY))
                .duration(Integer.parseInt(request.getParameter(DURATION)))
                .build();
    }
}
