package se.ifmo.blos.lab2.exceptions;

import java.io.Serial;

public class UnauthorizedException extends Exception {

  @Serial private static final long serialVersionUID = 4958441245498610922L;

  public UnauthorizedException(final String message) {
    super(message);
  }
}
