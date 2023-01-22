package com.my.testing.controller.actions;

import com.my.testing.exceptions.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

public interface Action {
    String execute(HttpServletRequest request) throws ServiceException;
}
