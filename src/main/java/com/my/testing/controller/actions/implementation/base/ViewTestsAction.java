package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.TestService;
import com.my.testing.utils.query.QueryBuilder;
import jakarta.servlet.http.HttpServletRequest;

import static com.my.testing.controller.actions.constants.Pages.VIEW_TESTS_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.utils.PaginationUtil.paginate;
import static com.my.testing.utils.QueryBuilderUtil.testQueryBuilder;

public class ViewTestsAction implements Action {
    private final TestService testService;

    public ViewTestsAction(AppContext appContext) {
        testService = appContext.getTestService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        request.setAttribute(TESTS, testService.getSorted(queryBuilder.getQuery()));
        int numberOfRecords = testService.getNumberOfRecords(queryBuilder.getRecordQuery());
        paginate(numberOfRecords, request);

        return VIEW_TESTS_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return testQueryBuilder()
                .setNameFilter(request.getParameter(NAME))
                .setSubjectFilter(request.getParameter(SUBJECT))
                .setSortField(request.getParameter(SORT_FIELD))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }


}
