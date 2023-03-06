package com.my.testing.dto;

import lombok.*;

import java.io.*;

/**
 * QuestionDTO class.
 * Use QuestionDTO.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Data
@Builder
public class QuestionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String text;
    private long testId;

}
