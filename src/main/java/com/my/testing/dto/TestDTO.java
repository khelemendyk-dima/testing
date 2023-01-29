package com.my.testing.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class TestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String subject;
    private String difficulty;
    private int duration;
    private int numberOfQueries;
}
