package com.my.testing.model.entities;

import lombok.*;

import java.io.*;

/**
 * Test entity class. Matches table 'test' in database.
 * Use Test.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@Data
@Builder
public class Test implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private int subjectId;
    private int difficultyId;
    private int duration;
    private int numberOfQueries;
}
