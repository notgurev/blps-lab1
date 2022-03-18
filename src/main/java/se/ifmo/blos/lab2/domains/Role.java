package se.ifmo.blos.lab2.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.var;

import static java.lang.String.format;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MANAGER;

    @JsonCreator
    public static Role forValue(final String value) {
        for (final var role : values()) {
            if (role.toString().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException(format("No role with text %s found.", value));
    }
}
