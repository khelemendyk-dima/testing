package com.my.testing.model.services.implementation;

import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.UserDAO;
import com.my.testing.model.entities.User;
import com.my.testing.model.entities.enums.Role;
import com.my.testing.model.services.UserService;

import java.util.*;
import java.util.stream.IntStream;

import static com.my.testing.exceptions.constants.Message.*;
import static com.my.testing.utils.ConvertorUtil.*;
import static com.my.testing.utils.PasswordHashUtil.*;
import static com.my.testing.utils.ValidatorUtil.*;

/**
 * Implementation of UserService interface.
 * Contains userDAO field to work with UserDAO
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();

    /**
     * @param userDAO DAO to work with database
     */
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Gets UserDTO from action and calls DAO to add new entity. Validates user's fields, passwords.
     * Encode password for database. Converts UserDTO to User
     * @param userDTO DTO to be added as entity to database
     * @param password password to be added to entity
     * @param confirmPassword will check if passwords match
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException with specific message,
     * PasswordMatchingException, DuplicateEmailException
     */
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

    /**
     * Obtains instance of User from DAO by email. Checks if email valid. Convert User to UserDTO
     * @param email to find user in database
     * @return DTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException
     */
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

    /**
     * Obtains instance of User from DAO by id. Checks if id valid. Converts User to UserDTO
     * @param idString id as a String to validate and convert to long
     * @return UserDTO instance
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException
     */
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

    /**
     * Obtains list of all instances of User from DAO. Converts Users to UserDTOs
     * @return list of UserDTOs
     * @throws ServiceException - may wrap DAOException
     */
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

    /**
     * Checks if parameters are correct. Obtains necessary User entity and checks if password matches.
     * Converts UserDTO to User
     * @param email to find user in database
     * @param password to check if matches with user password
     * @return UserDTO that matches this User entity
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException, IncorrectPasswordException
     */
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

    /**
     * Calls DAO to set new user's role
     * @param email to find user by email
     * @param roleId new role for user
     * @throws ServiceException - may wrap DAOException
     */
    @Override
    public void setRole(String email, int roleId) throws ServiceException {
        try {
            Role role = Role.getRole(roleId);
            userDAO.setUserRole(email, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Updates User's password. Validate passwords. Encode new password
     * @param userId id to find user by
     * @param password old password
     * @param newPass new password
     * @param confirmPass should match new password
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException,
     * NoSuchUserException, IncorrectPasswordException, PasswordMatchingException
     */
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

    /**
     * Calls DAO to reset User with new password by id. Generate new password. Encode new password.
     * @param userId id to find user by
     * @return new password for user
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException, NoSuchUserException.
     */
    @Override
    public String resetPassword(long userId) throws ServiceException {
        String newPass = generatePassword();
        try {
            User user = User.builder().id(userId).password(encode(newPass)).build();
            userDAO.updatePassword(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return newPass;
    }

    /**
     * Updates User's email, name, surname. Validates UserDTO. Converts UserDTO to User
     * @param entity DTO to be updated
     * @throws ServiceException - may wrap DAOException or be thrown as IncorrectFormatException or
     * DuplicateEmailException.
     */
    @Override
    public void update(UserDTO entity) throws ServiceException {
        validateUser(entity);
        try {
            userDAO.update(convertDTOToUser(entity));
        } catch (DAOException e) {
           checkExceptionType(e);
        }

    }

    /**
     * Deletes User entity from database. Validates id.
     * @param idString id as a String to validate and convert to long
     * @throws ServiceException - may wrap DAOException or be thrown as NoSuchUserException
     */
    @Override
    public void delete(String idString) throws ServiceException {
        long userId = getUserId(idString);
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Validates user's email, name and surname
     * @param userDTO entity that need to validate
     * @throws IncorrectFormatException if something isn't valid
     */
    private void validateUser(UserDTO userDTO) throws IncorrectFormatException {
        validateEmail(userDTO.getEmail());
        validateName(userDTO.getName(), ENTER_CORRECT_NAME);
        validateName(userDTO.getSurname(), ENTER_CORRECT_SURNAME);
    }

    /**
     * Specify Service Exception
     * @param e exception thrown by DAO
     * @throws ServiceException in case of general SQLException and DuplicateEmailException
     * if email is already registered
     */
    private void checkExceptionType(DAOException e) throws ServiceException {
        if (e.getMessage().contains("Duplicate")) {
            throw new DuplicateEmailException();
        } else {
            throw new ServiceException(e);
        }
    }

    /**
     * Obtains new password for User
     * @return generated password
     */
    private String generatePassword() {
        return IntStream.generate(() -> random.nextInt(SYMBOLS.length()))
                .map(SYMBOLS::charAt)
                .limit(17)
                .collect(() -> new StringBuilder("1aA"), StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
