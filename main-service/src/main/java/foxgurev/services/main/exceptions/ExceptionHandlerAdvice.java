package foxgurev.services.main.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HTTPError> handleInternalErrors(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new HTTPError("Internal server error"));
    }

    @ExceptionHandler(VisibleException.class) // does not work on filters
    public ResponseEntity<HTTPError> handleBadRequests(VisibleException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new HTTPError(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HTTPError> handleBadRequests(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new HTTPError("Access denied"));
    }

    @Data
    static class HTTPError {
        final String error;
    }
}
