package com.my.testing.model.service;

import com.my.testing.dto.UserDTO;
import com.my.testing.exceptions.*;
import com.my.testing.model.dao.UserDAO;
import com.my.testing.model.entities.User;
import com.my.testing.model.entities.enums.Role;
import com.my.testing.model.services.UserService;
import com.my.testing.model.services.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.my.testing.Constants.*;
import static com.my.testing.exceptions.constants.Message.*;
import static com.my.testing.utils.PasswordHashUtil.encode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private final UserDAO userDAO = mock(UserDAO.class);
    private final UserService userService = new UserServiceImpl(userDAO);

    @Test
    void testCorrectRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        assertDoesNotThrow(() -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "email.com", "email@com", "email@epam.m", "email@epam.mmmmmmmm", "email@epam.444"})
    void testWrongEmailRegistration(String email) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(email);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullEmailRegistration(String email) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(email);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pass1", "PASSWORD", "password", "12345678", "PASSWORD1", "password1", "password1Password1234"})
    void testWrongPassRegistration(String password) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, password, password));
        assertEquals(ENTER_CORRECT_PASSWORD, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullPassRegistration(String password) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, password, password));
        assertEquals(ENTER_CORRECT_PASSWORD, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "!!!", "$*$", "Julian 2", "X A-12"})
    void testWrongNameRegistration(String name) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullNameRegistration(String name) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "!!!", "$*$", "Julian 2", "X A-12"})
    void testWrongSurnameRegistration(String surname) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullSurnameRegistration(String surname) throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @Test
    void testDuplicateEmailRegistration() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        DuplicateEmailException e = assertThrows(DuplicateEmailException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(DUPLICATE_EMAIL, e.getMessage());
    }

    @Test
    void testPasswordsDoNotMatch() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        PasswordMatchingException e = assertThrows(PasswordMatchingException.class,
                () -> userService.add(userDTO, PASSWORD_VALUE, INCORRECT_PASSWORD));
        assertEquals(PASSWORD_MATCHING, e.getMessage());
    }

    @Test
    void testSignIn() throws DAOException, ServiceException {
        User user = getTestUser();
        user.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.of(user));
        assertEquals(getTestUserDTO(), userService.signIn(EMAIL_VALUE, PASSWORD_VALUE));
    }

    @Test
    void testWrongEmailSignIn() throws DAOException {
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.signIn(EMAIL_VALUE, PASSWORD_VALUE));
    }

    @Test
    void testWrongPasswordSignIn() throws DAOException {
        User user = getTestUser();
        user.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.of(user));
        assertThrows(IncorrectPasswordException.class, () -> userService.signIn(EMAIL_VALUE, INCORRECT_PASSWORD));
    }

    @Test
    void testNullSignIn() {
        assertThrows(ServiceException.class, () -> userService.signIn(null, null));
    }

    @Test
    void testGetById() throws DAOException, ServiceException {
        when(userDAO.getById(ID_VALUE)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserDTO(), userService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetByIdNoUser() throws DAOException {
        when(userDAO.getById(ID_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.getById(String.valueOf(ID_VALUE)));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testGetByIdWrongId(String id) {
        assertThrows(NoSuchUserException.class, () -> userService.getById(id));
    }

    @Test
    void testGetByIdWrongId2() {
        assertThrows(NoSuchUserException.class,() -> userService.getById("id"));
    }

    @Test
    void testNullGetById() {
        assertThrows(ServiceException.class,() -> userService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorGetById() throws DAOException {
        when(userDAO.getById(ID_VALUE)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class,() -> userService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetByEmail() throws DAOException, ServiceException {
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserDTO(), userService.getByEmail(EMAIL_VALUE));
    }

    @Test
    void testGetByEmailNoUser() throws DAOException {
        when(userDAO.getByEmail(EMAIL_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class, () -> userService.getByEmail(EMAIL_VALUE));
    }

    @Test
    void testGetByEmailIncorrectFormat() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class,
                () -> userService.getByEmail(INCORRECT_EMAIL));
        assertEquals(ENTER_CORRECT_EMAIL, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testGetByEmailIncorrectFormat2(String email) {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class,
                () -> userService.getByEmail(email));
        assertEquals(ENTER_CORRECT_EMAIL, exception.getMessage());
    }

    @Test
    void testSQLErrorGetByEmail() throws DAOException {
        when(userDAO.getByEmail(EMAIL_VALUE)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.getByEmail(EMAIL_VALUE));
    }

    @Test
    void testViewUsers() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOS = new ArrayList<>();
        users.add(getTestUser());
        userDTOS.add(getTestUserDTO());
        when(userDAO.getAll()).thenReturn(users);
        assertIterableEquals(userDTOS, userService.getAll());
    }

    @Test
    void testSQLErrorViewUsers() throws DAOException {
        when(userDAO.getAll()).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, userService::getAll);
    }

    @Test
    void testEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        assertDoesNotThrow(() -> userService.update(getTestUserDTO()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "email.com", "email@com", "email@epam.m", "email@epam.mmmmmmmm", "email@epam.444"})
    void testWrongEmailEditProfile(String email) throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(email);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullEmailEditProfile(String email) throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(email);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "!!!", "$*$", "Julian 2", "X Æ A-12"})
    void testWrongNameEditProfile(String name) throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullNameEditProfile(String name) throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(name);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "!!!", "$*$", "Julian 2", "X Æ A-12"})
    void testWrongSurnameEditProfile(String surname) throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullSurnameEditProfile(String surname) throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(surname);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @Test
    void testDuplicateEmailEditProfile() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        DuplicateEmailException e = assertThrows(DuplicateEmailException.class ,
                () -> userService.update(userDTO));
        assertEquals(DUPLICATE_EMAIL, e.getMessage());
    }

    @Test
    void testSQLErrorEditProfile() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        ServiceException e = assertThrows(ServiceException.class , () -> userService.update(userDTO));
        assertEquals(e.getCause(), exception);
        assertFalse(e.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testChangePassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User user = getTestUser();
        user.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ID_VALUE)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.changePassword(ID_VALUE, PASSWORD_VALUE, PASSWORD_VALUE, PASSWORD_VALUE));
    }

    @Test
    void testChangeWrongNewPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User user = getTestUser();
        user.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ID_VALUE)).thenReturn(Optional.of(user));
        assertThrows(IncorrectFormatException.class,
                () -> userService.changePassword(ID_VALUE, PASSWORD_VALUE, WRONG_PASSWORD, WRONG_PASSWORD));
    }

    @Test
    void testChangeWrongOldPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User user = getTestUser();
        user.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ID_VALUE)).thenReturn(Optional.of(user));
        assertThrows(IncorrectPasswordException.class,
                () -> userService.changePassword(ID_VALUE, WRONG_PASSWORD, PASSWORD_VALUE, PASSWORD_VALUE));
    }

    @Test
    void testChangeWrongConfirmPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User user = getTestUser();
        user.setPassword(encode(PASSWORD_VALUE));
        when(userDAO.getById(ID_VALUE)).thenReturn(Optional.of(user));
        assertThrows(PasswordMatchingException.class,
                () -> userService.changePassword(ID_VALUE, PASSWORD_VALUE, PASSWORD_VALUE, WRONG_PASSWORD));
    }

    @Test
    void testChangeNullPassword() {
        assertThrows(ServiceException.class,
                () -> userService.changePassword(ID_VALUE, null, null, null));
    }

    @Test
    void testSQLErrorChangePassword() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).getById(anyLong());
        ServiceException e = assertThrows(ServiceException.class,
                () -> userService.changePassword(ID_VALUE, PASSWORD_VALUE, PASSWORD_VALUE, PASSWORD_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testSetRole() throws DAOException {
        doNothing().when(userDAO).setUserRole(anyString(), isA(Role.class));
        assertDoesNotThrow(() -> userService.setRole(EMAIL_VALUE, ROLE_ID_VALUE));
    }

    @Test
    void testSQLErrorSetRole() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).setUserRole(anyString(), isA(Role.class));
        ServiceException e = assertThrows(ServiceException.class,
                () -> userService.setRole(EMAIL_VALUE, ROLE_ID_VALUE));
        assertEquals(e.getCause(), exception);
    }

    @Test
    void testDeleteUser() throws DAOException {
        doNothing().when(userDAO).delete(anyLong());
        assertDoesNotThrow(() -> userService.delete(String.valueOf(ID_VALUE)));
    }

    @Test
    void testSQLErrorDeleteUser() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).delete(anyLong());
        ServiceException e = assertThrows(ServiceException.class, () -> userService.delete(String.valueOf(ID_VALUE)));
        assertEquals(e.getCause(), exception);
    }

    private UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .name(NAME_VALUE)
                .surname(SURNAME_VALUE)
                .role(ROLE_STUDENT)
                .build();
    }

    private User getTestUser() {
        return User.builder()
                .id(ID_VALUE)
                .email(EMAIL_VALUE)
                .name(NAME_VALUE)
                .surname(SURNAME_VALUE)
                .password(PASSWORD_VALUE)
                .roleId(ROLE_ID_VALUE)
                .build();
    }
}
