package com.conduit.medium.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * enums to map errors.
 */
@Getter
public enum Error {
  GENERIC_ERROR("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR),
  //auth errors
  USERNAME_NOT_FOUND_EXCEPTION("Given username not found",
      HttpStatus.NOT_FOUND),
  USERNAME_EXISTS_EXCEPTION("Given username is already registered", HttpStatus.BAD_REQUEST),
  EMAIL_EXISTS_EXCEPTION("Given email is already in use", HttpStatus.BAD_REQUEST),
  EMAIL_NOT_EXIST("Given email is not registered to any user, Unable to login",
      HttpStatus.UNAUTHORIZED),
  INVALID_PASSWORD("Incorrect password Entered", HttpStatus.UNAUTHORIZED);

  private final String errorMessage;
  private final HttpStatus httpStatus;

  Error(final String errorMessage, final HttpStatus httpStatus) {
    this.errorMessage = errorMessage;
    this.httpStatus = httpStatus;
  }
}
