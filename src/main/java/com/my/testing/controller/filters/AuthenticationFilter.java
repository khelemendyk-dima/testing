package com.my.testing.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.io.IOException;

import static com.my.testing.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static com.my.testing.controller.actions.constants.ParameterValues.ACCESS_DENIED;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.controller.filters.domain.Domain.getDomain;

/**
 * AuthenticationFilter class. Controls access to pages for anonymous user
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@WebFilter(urlPatterns = { "/controller/*", "*.jsp" })
public class AuthenticationFilter extends HttpFilter {
    private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    /**
     * Checks user in session and then checks if user has access to page or action.
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String servletPath = request.getServletPath();
        String action = request.getParameter(ACTION);

        if (isNoLoggedUser(request) && isAccessDenied(servletPath, action)) {
            logger.warn("Anonymous user tried to access forbidden page");
            request.setAttribute(ERROR, ACCESS_DENIED);
            request.getRequestDispatcher(SIGN_IN_PAGE).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isNoLoggedUser(HttpServletRequest request) {
        return request.getSession().getAttribute(LOGGED_USER) == null;
    }

    private boolean isAccessDenied(String servletPath, String action) {
        return getDomain(servletPath, action).checkAccess();
    }
}
