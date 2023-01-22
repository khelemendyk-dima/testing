package com.my.testing.model.entities.role;

public enum Role { ADMIN(1), STUDENT(2), BLOCKED(3);
    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static Role getRole(int value) {
        for (Role role : Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        return STUDENT;
    }
}