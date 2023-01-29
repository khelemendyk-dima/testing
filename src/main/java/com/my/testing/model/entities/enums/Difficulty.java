package com.my.testing.model.entities.enums;

public enum Difficulty {  EASY(1), MEDIUM(2), HARD(3);
    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Difficulty getDifficulty(int value) {
        for(Difficulty diff : Difficulty.values()) {
            if (diff.value == value) {
                return diff;
            }
        }

        return EASY;
    }
}
