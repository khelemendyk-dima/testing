package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.services.TestService;
import jakarta.servlet.http.HttpServletRequest;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.CREATE_TEST_ACTION;
import static com.my.testing.controller.actions.constants.ActionNames.SEARCH_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Pages.*;
import static com.my.testing.controller.actions.constants.Parameters.*;

public class CreateTestAction implements Action {
    private final TestService testService;

    public CreateTestAction(AppContext appContext) {
        testService = appContext.getTestService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, ERROR);
        transferTestDTOFromSessionToRequest(request);

        return getPath(request);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = "controller?action=" + SEARCH_TEST_ACTION + "&id=";
        TestDTO test = getTestDTO(request);
        request.getSession().setAttribute(TEST, test);
        try {
            testService.add(test);
            path += test.getId();
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
                .duration(Integer.parseInt(request.getParameter(DURATION)))
                .build();
    }
}
