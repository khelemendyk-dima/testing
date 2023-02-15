package com.my.testing.model.entities;

import lombok.*;

import java.io.*;

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
