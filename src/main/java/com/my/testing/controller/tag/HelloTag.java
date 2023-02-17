package com.my.testing.controller.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class HelloTag extends TagSupport {
    private String role;
    private String locale;

    public void setRole(String role) {
        this.role = role;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String greeting;

            if (locale.equalsIgnoreCase("en")) {
                greeting = doEN(role);
            } else {
                greeting = doUA(role);
            }

            pageContext.getOut().write(greeting);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }

    private String doEN(String role) {
        String greeting;

        if ("admin".equalsIgnoreCase(role)) {
            greeting = "Hello, admin!";
        } else {
            greeting = "Welcome, student!";
        }

        return greeting;
    }

    private String doUA(String role) {
        String greeting;

        if ("admin".equalsIgnoreCase(role)) {
            greeting = "Привіт, адмін!";
        } else {
            greeting = "Ласкаво просимо, студент!";
        }

        return greeting;
    }
}
