package com.my.testing.model.entities;

import lombok.*;

import java.io.*;

/**
 * TestResult entity class. Matches table 'test_result' in database.
 * Use TestResult.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Data
@Builder
public class TestResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private long userId;
    private long testId;
    private float result;
    private String testName;
}
