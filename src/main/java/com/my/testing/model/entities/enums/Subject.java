package com.my.testing.model.entities.enums;

import lombok.Getter;
import org.apache.logging.log4j.*;

/**
 * Subject entity enum. Matches table 'subject' in database.
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public enum Subject { ENGLISH(1), UKRAINIAN(2), MATH(3), HISTORY(4), BIOLOGY(5),
                      CHEMISTRY(6), PHYSICS(7);
    private static final Logger logger = LogManager.getLogger(Subject.class);
    @Getter
    private final int value;

    Subject(int value) {
        this.value = value;
    }

    public static Subject getSubject(int value) {
        for (Subject subject : Subject.values()) {
            if (subject.value == value) {
                return subject;
            }
        }
        logger.info("Invalid subject value. Set to ENGLISH");
        return ENGLISH;
    }
}