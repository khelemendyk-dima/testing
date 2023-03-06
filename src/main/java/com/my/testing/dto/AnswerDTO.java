package com.my.testing.dto;

import lombok.*;

import java.io.*;

/**
 * AnswerDTO class.
 * Use AnswerDTO.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Data
@Builder
public class AnswerDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private long id;
    private String text;
    private boolean isCorrect;
    private long questionId;
}
