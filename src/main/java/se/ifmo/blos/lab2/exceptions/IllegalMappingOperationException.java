package se.ifmo.blos.lab2.exceptions;


public class IllegalMappingOperationException extends RuntimeException {
    public IllegalMappingOperationException() {
        super();
    }

    public IllegalMappingOperationException(final String message) {
        super(message);
    }
}
