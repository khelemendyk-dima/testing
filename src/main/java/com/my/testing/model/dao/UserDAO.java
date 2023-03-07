package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.User;
import com.my.testing.model.entities.enums.Role;

import java.util.Optional;

/**
 * User DAO interface.
 * Implements methods due to database type
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface UserDAO extends DAO<User> {

    /**
     * Obtains instance of User from database by email
     * @param email user's email
     * @return Optional.ofNullable user is null if there is no user
     * @throws DAOException is wrapper for SQLException
     */
    Optional<User> getByEmail(String email) throws DAOException;

    /**
     * Updates user's password
     * @param user should contain user id and new password
     * @throws DAOException is wrapper for SQLException
     */
    void updatePassword(User user) throws DAOException;

    /**
     * Sets new user's role
     * @param email user's email
     * @param role new role for user
     * @throws DAOException is wrapper for SQLException
     */
    void setUserRole(String email, Role role) throws DAOException;
}
