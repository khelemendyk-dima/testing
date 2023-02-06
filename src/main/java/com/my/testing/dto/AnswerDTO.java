package com.my.testing.dto;

import lombok.*;

import java.io.*;

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
