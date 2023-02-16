package com.my.testing.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import static com.my.testing.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static com.my.testing.controller.actions.constants.ParameterValues.ACCESS_DENIED;
import static com.my.testing.controller.actions.constants.Parameters.*;
import static com.my.testing.controller.filters.domain.Domain.getDomain;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String role = (String) httpRequest.getSession().getAttribute(ROLE);
        String servletPath = httpRequest.getServletPath();
        String action = httpRequest.getParameter(ACTION);
        if (role != null && isAccessDenied(servletPath, action, role)) {
            httpRequest.setAttribute(MESSAGE, ACCESS_DENIED);
            request.getRequestDispatcher(SIGN_IN_PAGE).forward(request,response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAccessDenied(String servletPath, String action, String role) {
        return getDomain(servletPath, action, role).checkAccess();
    }
}
