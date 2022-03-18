package se.ifmo.blos.lab2.exceptions;

public class ResourceAlreadyExistsException extends Exception {


    public ResourceAlreadyExistsException() {
        super();
    }

    public ResourceAlreadyExistsException(final String message) {
        super(message);
    }
}
