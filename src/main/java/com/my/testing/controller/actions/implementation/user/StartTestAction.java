package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.exceptions.ServiceException;
import jakarta.servlet.http.*;

import java.time.LocalDateTime;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.SOLVE_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is StartTestAction class. Accessible by any logged user. Allows to start test
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class StartTestAction implements Action {
    /**
     * Gets duration from request and calculates finish time for solving test
     * @param request to get duration of the test and test's id
     * @return path to redirect to solve test action for starting test
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        int duration = Integer.parseInt(request.getParameter(DURATION));

        request.getSession().setAttribute(FINISH_TEST_TIME, LocalDateTime.now().plusMinutes(duration));
        return getActionToRedirect(SOLVE_TEST_ACTION, ID, request.getParameter(ID));
    }
}
