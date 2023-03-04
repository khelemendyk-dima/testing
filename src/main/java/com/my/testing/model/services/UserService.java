package com.my.testing.model.services;

import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.ServiceException;

public interface UserService extends Service<UserDTO> {
    void add(UserDTO userDTO, String password, String confirmPassword) throws ServiceException;

    UserDTO signIn(String email, String password) throws ServiceException;
    UserDTO getByEmail(String email) throws ServiceException;
    void setRole(String email, int roleId) throws ServiceException;

    void changePassword(long userId, String password, String newPass, String confirmPass) throws ServiceException;
    String resetPassword(long userId) throws ServiceException;
}
