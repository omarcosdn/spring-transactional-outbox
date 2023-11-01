package io.github.omarcosdn.outbox.app.exceptions;


public class DomainException extends RuntimeException {

  public DomainException(final String message) {
    super(message);
  }
}
