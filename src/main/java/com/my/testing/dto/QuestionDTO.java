package com.my.testing.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class QuestionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String text;
    private long testId;

}
