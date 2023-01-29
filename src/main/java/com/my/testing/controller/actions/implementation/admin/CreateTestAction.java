package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.IncorrectFormatException;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.TestService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.CREATE_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Pages.CREATE_TEST_PAGE;
import static com.my.testing.controller.actions.constants.Pages.VIEW_TEST_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class CreateTestAction implements Action {
    private final TestService testService;

    public CreateTestAction(AppContext appContext) {
        testService = appContext.getTestService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePosts(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, ERROR);
        return getPath(request);
    }

    private String executePosts(HttpServletRequest request) throws ServiceException {
        String path = VIEW_TEST_PAGE;
        TestDTO test = getTestDTO(request);
        request.getSession().setAttribute(TEST, test);
        try {
            testService.add(test);
        } catch (IncorrectFormatException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = CREATE_TEST_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(CREATE_TEST_ACTION);
    }

    private TestDTO getTestDTO(HttpServletRequest request) {
        return TestDTO.builder()
                .name(request.getParameter(NAME))
                .subject(request.getParameter(SUBJECT))
                .difficulty(request.getParameter(DIFFICULTY))
                .duration(calcDuration(request.getParameter(DURATION)))
                .build();
    }

    private static int calcDuration(String stringDur) {
        int[] hoursAndMinutes = Arrays.stream(stringDur.split(":"))
                                           .mapToInt(Integer::parseInt)
                                           .toArray();
        return hoursAndMinutes[0] * 60 + hoursAndMinutes[1];
    }

}
