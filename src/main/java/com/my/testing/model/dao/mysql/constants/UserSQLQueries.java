package com.my.testing.model.dao.mysql.constants;

import lombok.*;

/**
 * Class that contains all queries for UserDAO
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserSQLQueries {
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id=?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
    public static final String GET_ALL_USERS = "SELECT * FROM user";
    public static final String ADD_USER = "INSERT INTO user (email, password, name, surname) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_USER = "UPDATE user SET email=?, name=?, surname=? WHERE id=?";
    public static final String UPDATE_PASSWORD = "UPDATE user SET password=? WHERE id=?";
    public static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    public static final String SET_USER_ROLE = "UPDATE user SET role_id=? WHERE email=?";
}
