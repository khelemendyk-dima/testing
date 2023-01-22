package com.my.testing.model.dao;

import com.my.testing.exceptions.DAOException;
import com.my.testing.model.entities.User;

import java.util.Optional;

public interface UserDAO extends DAO<User> {

    Optional<User> getByEmail(String email) throws DAOException;
    void updatePassword(User user) throws DAOException;
}
