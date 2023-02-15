package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.TestResultService;
import jakarta.servlet.http.HttpServletRequest;

import static com.my.testing.controller.actions.constants.Pages.VIEW_RESULTS_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class ViewResultsAction implements Action {
    private final TestResultService testResultService;

    public ViewResultsAction(AppContext appContext) {
        testResultService = appContext.getTestResultService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        UserDTO loggedUser = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        String userId = String.valueOf(loggedUser.getId());

        request.setAttribute(TEST_RESULTS, testResultService.getAllByUserId(userId));

        return VIEW_RESULTS_PAGE;
    }
}
