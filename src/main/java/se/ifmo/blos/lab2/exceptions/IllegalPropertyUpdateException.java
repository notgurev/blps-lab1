package se.ifmo.blos.lab2.exceptions;

import java.io.Serial;

public class IllegalPropertyUpdateException extends Exception {

  @Serial private static final long serialVersionUID = 4502144543399649502L;

  public IllegalPropertyUpdateException() {
    super();
  }

  public IllegalPropertyUpdateException(final String message) {
    super(message);
  }
}
