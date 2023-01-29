package com.my.testing.model.entities.enums;

public enum Subject { ENGLISH(1), UKRAINIAN(2), MATH(3), HISTORY(4), BIOLOGY(5),
                      CHEMISTRY(6), PHYSICS(7);
    private final int value;

    Subject(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Subject getSubject(int value) {
        for (Subject subject : Subject.values()) {
            if (subject.value == value) {
                return subject;
            }
        }

        return ENGLISH;
    }
}