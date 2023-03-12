package com.my.testing.controller.actions.constants;

import lombok.*;

/**
 * ParameterValues class. It contains all constant parameters and attributes values
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParameterValues {

    /** Some common messages for user */
    public static final String CHECK_EMAIL = "check.email";
    public static final String SUCCEED_REGISTERED = "succeed.registered";
    public static final String SUCCEED_UPDATED = "succeed.updated";

    /** Some common errors for user */
    public static final String ACCESS_DENIED = "access.denied";

    /** Available parameters and attributes values for order */
    public static final String ASCENDING_ORDER = "ASC";
    public static final String DESCENDING_ORDER = "DESC";
}
