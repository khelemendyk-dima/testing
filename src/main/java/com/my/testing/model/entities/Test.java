package com.my.testing.model.entities;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

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
