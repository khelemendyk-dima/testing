package com.my.testing.model.entities;

import lombok.*;
import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private transient String password;
    private String name;
    private String surname;
    private int roleId;
}
