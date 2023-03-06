package com.my.testing.model.entities;

import lombok.*;

import java.io.*;

/**
 * User entity class. Matches table 'user' in database.
 * Use User.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Data
@Builder
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private transient String password;
    private String name;
    private String surname;
    private int roleId;
}
