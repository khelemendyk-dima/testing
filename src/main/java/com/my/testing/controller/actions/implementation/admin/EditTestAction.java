package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.TestDTO;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.TestService;
import jakarta.servlet.http.HttpServletRequest;

import static com.my.testing.controller.actions.ActionUtil.*;
import static com.my.testing.controller.actions.constants.ActionNames.EDIT_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Pages.EDIT_TEST_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.exceptions.constants.Message.ENTER_CORRECT_NAME;

public class EditTestAction implements Action {
    private final TestService testService;

    public EditTestAction(AppContext appContext) {
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

    private String executePost(HttpServletRequest request) {
        TestDTO test = getTestDTO(request);
        String path = "controller?action=search-test&id=" + test.getId();

        try {
            testService.update(test);
        } catch (ServiceException e) {
            path = EDIT_TEST_PAGE;
            request.getSession().setAttribute(ERROR, ENTER_CORRECT_NAME);
            request.getSession().setAttribute(TEST, test);
        }

        request.getSession().setAttribute(CURRENT_PATH, path);

        return getActionToRedirect(EDIT_TEST_ACTION);
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
