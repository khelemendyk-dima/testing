package com.my.testing.model.entities;

import lombok.*;

import java.io.*;

@Data
@Builder
public class Question implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String text;
    private long testId;
}
