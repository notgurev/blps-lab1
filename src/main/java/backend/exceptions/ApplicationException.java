package backend.exceptions;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ApplicationErrorDto error;

    public ApplicationException(ApplicationErrorDto error) {
        super(error.getMessage());
        this.error = error;
    }
}
