package com.my.testing.dto;

import lombok.*;

import java.io.*;

/**
 * UserDTO class. Password field is absent.
 * Use UserDTO.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Data
@Builder
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private String name;
    private String surname;
    private String role;
}