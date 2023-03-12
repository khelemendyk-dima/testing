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
 * AuthorizationFilter class. Controls access to pages for logged user
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@WebFilter(urlPatterns = { "/controller/*", "*.jsp" })
public class AuthorizationFilter extends HttpFilter {
    private static final Logger logger = LogManager.getLogger(AuthorizationFilter.class);

    /**
     * Checks for role in session and then checks if user has access to page or action
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String role = (String) request.getSession().getAttribute(ROLE);
        String servletPath = request.getServletPath();
        String action = request.getParameter(ACTION);
        if (role != null && isAccessDenied(servletPath, action, role)) {
            logger.warn(String.format("%s tried to access forbidden page", request.getSession().getAttribute(LOGGED_USER)));
            request.setAttribute(ERROR, ACCESS_DENIED);
            request.getRequestDispatcher(SIGN_IN_PAGE).forward(request,response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAccessDenied(String servletPath, String action, String role) {
        return getDomain(servletPath, action, role).checkAccess();
    }
}
