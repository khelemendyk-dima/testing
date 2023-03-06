package com.my.testing.dto;

import lombok.*;

import java.io.*;

/**
 * TestDTO class. Fields are similar to Test entity, except subject and difficulty
 * which is string value of concrete enum
 * Use TestDTO.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
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
