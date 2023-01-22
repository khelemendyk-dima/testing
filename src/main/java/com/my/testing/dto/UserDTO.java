package com.my.testing.dto;

import lombok.*;
import java.io.Serializable;

@Data
@Builder
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private String name;
    private String surname;
    private String role;
}