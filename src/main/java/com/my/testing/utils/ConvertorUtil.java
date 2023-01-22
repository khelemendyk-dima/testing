package com.my.testing.utils;

import com.my.testing.dto.UserDTO;
import com.my.testing.model.entities.User;
import com.my.testing.model.entities.role.Role;

public final class ConvertorUtil {

    private ConvertorUtil() {}

    public static User convertDTOToUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .build();
    }

    public static UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .role(String.valueOf(Role.getRole(user.getRoleId())))
                .build();
    }
}
