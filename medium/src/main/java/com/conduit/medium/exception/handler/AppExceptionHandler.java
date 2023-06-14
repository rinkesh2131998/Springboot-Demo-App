package com.conduit.medium.exception.handler;

import com.conduit.medium.exception.ApplicationException;
import com.conduit.medium.model.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * controller advice to handle exceptions thrown by all apis.
 */
@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

  /**
   * handler to log all custom errors on api, arising in case of any business logic.
   *
   * @param applicationException exception details
   * @return http response with error message and status
   */
  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<Errors> handleApplicationException(
      final ApplicationException applicationException) {
    final Errors errors = new Errors(applicationException.getMessage());
    return new ResponseEntity<>(errors, applicationException.getError().getHttpStatus());
  }
}
