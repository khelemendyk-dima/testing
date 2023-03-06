package com.my.testing.dto;

import lombok.*;

import java.io.*;


/**
 * TestResultDTO class.
 * Use TestResultDTO.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Data
@Builder
public class TestResultDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private long userId;
    private long testId;
    private float result;
    private String testName;
}
