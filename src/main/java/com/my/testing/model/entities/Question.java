package com.my.testing.model.entities;

import lombok.*;

import java.io.*;

/**
 * Question entity class. Matches table 'question' in database.
 * Use Question.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Data
@Builder
public class Question implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String text;
    private long testId;
}
