package com.my.testing.controller.actions.implementation.user;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.TestService;
import com.my.testing.utils.query.QueryBuilder;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import static com.my.testing.controller.actions.constants.Pages.VIEW_TESTS_PAGE;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.utils.PaginationUtil.paginate;
import static com.my.testing.utils.QueryBuilderUtil.testQueryBuilder;

/**
 * This is ViewTestsAction class. Accessible by any logged user. Allows to return list of sorted tests.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class ViewTestsAction implements Action {
    private static final Logger logger = LogManager.getLogger(ViewTestsAction.class);
    private final TestService testService;

    /**
     * @param appContext contains TestService instance to use in action
     */
    public ViewTestsAction(AppContext appContext) {
        testService = appContext.getTestService();
    }

    /**
     * Builds required query for service, sets test list in request and obtains required path. Also sets all required
     * for pagination attributes
     *
     * @param request to get queries parameters and put tests list in request
     * @return view tests page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        request.setAttribute(TESTS, testService.getSorted(queryBuilder.getQuery()));
        int numberOfRecords = testService.getNumberOfRecords(queryBuilder.getRecordQuery());
        paginate(numberOfRecords, request);
        logger.info("List of tests was successfully returned");

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
