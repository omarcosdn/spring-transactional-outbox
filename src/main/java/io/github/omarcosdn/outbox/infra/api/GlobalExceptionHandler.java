package io.github.omarcosdn.outbox.infra.api;

import io.github.omarcosdn.outbox.app.exceptions.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ApiError> handleException(final Exception e) {
    return ResponseEntity.internalServerError().body(ApiError.from(e));
  }

  @ExceptionHandler(value = DomainException.class)
  public ResponseEntity<ApiError> handleDomainException(final DomainException e) {
    return ResponseEntity.unprocessableEntity().body(ApiError.from(e));
  }

  public record ApiError(String code, String message) {

    static ApiError from(final Exception e) {
      final var code = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
      logger.error(code, e);
      return new ApiError(code, "An unexpected error occurred while processing your request.");
    }

    static ApiError from(final DomainException e) {
      final var code = HttpStatus.BAD_REQUEST.getReasonPhrase();
      return new ApiError(code, e.getMessage());
    }
  }
}
