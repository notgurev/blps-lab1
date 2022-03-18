package se.ifmo.blos.lab2.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.var;

import static java.lang.String.format;

public enum CreditStatus {
    CREATED,
    ACCEPTED,
    REJECTED,
    REVIEW;

    @JsonCreator
    public static CreditStatus forValue(final String value) {
        for (var status : values()) {
            if (status.toString().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException(format("No status with text %s found.", value));
    }
}
