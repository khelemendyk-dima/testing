package com.my.testing.model.services.implementation;

import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.UserDAO;
import com.my.testing.model.entities.User;
import com.my.testing.model.entities.enums.Role;
import com.my.testing.model.services.UserService;

import java.util.*;

import static com.my.testing.exceptions.constants.Message.*;
import static com.my.testing.utils.ConvertorUtil.*;
import static com.my.testing.utils.PasswordHashUtil.*;
import static com.my.testing.utils.ValidatorUtil.*;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void add(UserDTO userDTO, String password, String confirmPassword) throws ServiceException {
        validateUser(userDTO);
        validatePassword(password);
        checkPasswordMatching(password, confirmPassword);
        User user = convertDTOToUser(userDTO);
        user.setPassword(encode(password));
        try {
            userDAO.add(user);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    @Override
    public UserDTO signIn(String email, String password) throws ServiceException {
        checkStrings(email, password);
        UserDTO userDTO;
        try {
            User user = userDAO.getByEmail(email).orElseThrow(NoSuchUserException::new);
            verify(user.getPassword(), password);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return userDTO;
    }

    @Override
    public UserDTO getByEmail(String email) throws ServiceException {
        validateEmail(email);
        UserDTO userDTO;
        try {
            User user = userDAO.getByEmail(email).orElseThrow(NoSuchUserException::new);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public void setRole(String email, int roleId) throws ServiceException {
        try {
            Role role = Role.getRole(roleId);
            userDAO.setUserRole(email, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changePassword(long userId, String password, String newPass, String confirmPass) throws ServiceException {
        checkStrings(password, newPass, confirmPass);
        try {
            User user = userDAO.getById(userId).orElseThrow(NoSuchUserException::new);
            verify(user.getPassword(), password);
            checkPasswordMatching(newPass, confirmPass);
            validatePassword(newPass);
            User userToUpdate = User.builder()
                                    .id(userId)
                                    .password(encode(newPass))
                                    .build();
            userDAO.updatePassword(userToUpdate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDTO getById(String idString) throws ServiceException {
        UserDTO userDTO;
        long userId = getUserId(idString);
        try {
            User user = userDAO.getById(userId).orElseThrow(NoSuchUserException::new);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return userDTO;
    }

    @Override
    public List<UserDTO> getAll() throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userDAO.getAll();
            users.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return userDTOS;
    }

    @Override
    public void update(UserDTO entity) throws ServiceException {
        validateUser(entity);
        try {
            userDAO.update(convertDTOToUser(entity));
        } catch (DAOException e) {
           checkExceptionType(e);
        }

    }

    @Override
    public void delete(String idString) throws ServiceException {
        long userId = getUserId(idString);
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private void validateUser(UserDTO userDTO) throws IncorrectFormatException {
        validateEmail(userDTO.getEmail());
        validateName(userDTO.getName(), ENTER_CORRECT_NAME);
        validateName(userDTO.getSurname(), ENTER_CORRECT_SURNAME);
    }

    private void checkExceptionType(DAOException e) throws ServiceException {
        if (e.getMessage().contains("Duplicate")) {
            throw new DuplicateEmailException();
        } else {
            throw new ServiceException(e);
        }
    }
}
