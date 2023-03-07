package com.my.testing.model.services;

import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.ServiceException;

/**
 * UserService interface.
 * Implements all methods in concrete UserService
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public interface UserService extends Service<UserDTO> {

    /**
     * Calls DAO to add new entity
     * @param userDTO DTO to be added as entity to database
     * @param password password to be added to entity
     * @param confirmPassword will check if passwords match
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void add(UserDTO userDTO, String password, String confirmPassword) throws ServiceException;

    /**
     * Obtains necessary User entity and checks if password matches
     * @param email to find user in database
     * @param password to check if matches with user password
     * @return UserDTO - that matches User entity
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    UserDTO signIn(String email, String password) throws ServiceException;

    /**
     * Obtains necessary User entity
     * @param email to find user in database
     * @return UserDTO - that matches User entity
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    UserDTO getByEmail(String email) throws ServiceException;

    /**
     * Calls DAO to set new user role
     * @param email to find user by email
     * @param roleId new role for user
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void setRole(String email, int roleId) throws ServiceException;

    /**
     * Calls DAO to update User with new password
     * @param userId id to find user by
     * @param password old password
     * @param newPass new password
     * @param confirmPass should match new password
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void changePassword(long userId, String password, String newPass, String confirmPass) throws ServiceException;

    /**
     * Calls DAO to update User with new password if user forgot old one
     * @param userId id to find user by
     * @return new generated password for user
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    String resetPassword(long userId) throws ServiceException;
}
