package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.TestResultService;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.constants.Pages.VIEW_RESULTS_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is ViewResultsAction class. Accessible by any logged user. Allows to return list of test results.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class ViewResultsAction implements Action {
    private static final Logger logger = LogManager.getLogger(ViewResultsAction.class);
    private final TestResultService testResultService;

    /**
     * @param appContext contains TestResultService instance to use in action
     */
    public ViewResultsAction(AppContext appContext) {
        testResultService = appContext.getTestResultService();
    }

    /**
     * Gets logged user from request. Then sets to request all test results that user has passed.
     * @param request to get logged user
     * @return view results page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        UserDTO loggedUser = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        String userId = String.valueOf(loggedUser.getId());

        request.setAttribute(TEST_RESULTS, testResultService.getAllByUserId(userId));
        logger.info("List of test results was successfully returned");

        return VIEW_RESULTS_PAGE;
    }
}
