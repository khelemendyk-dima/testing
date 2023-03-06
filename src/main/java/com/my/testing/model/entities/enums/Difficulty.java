package com.my.testing.model.entities.enums;

import lombok.Getter;
import org.apache.logging.log4j.*;

/**
 * Difficulty entity enum. Matches table 'difficulty' in database.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public enum Difficulty {  EASY(1), MEDIUM(2), HARD(3);
    private static final Logger logger = LogManager.getLogger(Difficulty.class);
    @Getter
    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    public static Difficulty getDifficulty(int value) {
        for(Difficulty diff : Difficulty.values()) {
            if (diff.value == value) {
                return diff;
            }
        }
        logger.info("Invalid difficulty value. Set to EASY");
        return EASY;
    }
}
