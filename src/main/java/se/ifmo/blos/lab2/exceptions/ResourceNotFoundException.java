package se.ifmo.blos.lab2.exceptions;

public class ResourceNotFoundException extends Exception {


    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
