package com.my.testing.model.entities;

import lombok.*;

import java.io.*;

/**
 * Answer entity class. Matches table 'answer' in database.
 * Use Answer.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Data
@Builder
public class Answer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private long id;
    private String text;
    private boolean isCorrect;
    private long questionId;
}
