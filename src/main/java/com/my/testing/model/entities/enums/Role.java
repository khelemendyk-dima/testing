package com.my.testing.model.entities.enums;

import lombok.*;
import org.apache.logging.log4j.*;

/**
 * Role entity enum. Matches table 'role' in database.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public enum Role { ADMIN(1), STUDENT(2), BLOCKED(3);
    private static final Logger logger = LogManager.getLogger(Role.class);
    @Getter
    private final int value;

    Role(int value) {
        this.value = value;
    }

    public static Role getRole(int value) {
        for (Role role : Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        logger.info("Invalid role value. Set to STUDENT");
        return STUDENT;
    }
}