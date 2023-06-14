package com.conduit.medium.exception;

import com.conduit.medium.enums.Error;
import lombok.Getter;

/**
 * custom error to be thrown, wherever custom error required.
 */
@Getter
public class ApplicationException extends RuntimeException {
  private final Error error;

  public ApplicationException(Error error) {
    super(error.getErrorMessage());
    this.error = error;
  }
}
