package com.my.testing.controller.actions.implementation.base;

import com.my.testing.controller.actions.Action;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.my.testing.controller.actions.constants.Pages.INDEX_PAGE;

public class DefaultAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return INDEX_PAGE;
    }
}
