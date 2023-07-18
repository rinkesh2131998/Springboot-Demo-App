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
  USERNAME_NOT_FOUND_EXCEPTION("Given username/email not found",
      HttpStatus.NOT_FOUND),
  USERNAME_EXISTS_EXCEPTION("Given username is already registered", HttpStatus.BAD_REQUEST),
  EMAIL_EXISTS_EXCEPTION("Given email is already in use", HttpStatus.BAD_REQUEST),
  EMAIL_NOT_EXIST("Given email is not registered to any user, Unable to login",
      HttpStatus.UNAUTHORIZED),
  INVALID_PASSWORD("Incorrect password Entered", HttpStatus.UNAUTHORIZED),
  FOLLOWER_REMOVAL_EXCEPTION("Unable to remove follower", HttpStatus.BAD_REQUEST),
  ARTICLE_NOT_FOUND_EXCEPTION("Unable to find the article with the given params",
      HttpStatus.BAD_REQUEST), ARTICLE_NOT_CREATED_EXCEPTION("Unable to create new article",
      HttpStatus.INTERNAL_SERVER_ERROR),
  ARTICLE_AUTHOR_INVALID_EXCEPTION("The author of article is not valid", HttpStatus.BAD_REQUEST),
  ARTICLE_TITLE_ALREADY_EXISTS_EXCEPTION("Unable to change article title as it already exists, "
      + "cancelling request", HttpStatus.BAD_REQUEST),
  ARTICLE_DELETE_EXCEPTION("Unable to delete the article, retry again",
      HttpStatus.INTERNAL_SERVER_ERROR),
  COMMENT_CREATION_FAILED_EXCEPTION("Unable to add comments to article",
      HttpStatus.INTERNAL_SERVER_ERROR),
  COMMENT_DELETION_FAILED_EXCEPTION("Unable to delete comment", HttpStatus.INTERNAL_SERVER_ERROR),
  ARTICLE_FAVORITE_FAILED_EXCEPTION("Unable to favorite an article",
      HttpStatus.INTERNAL_SERVER_ERROR),
  ARTICLE_UNFAVOURITE_EXCEPTION("Unable to remove favorite article for user",
      HttpStatus.INTERNAL_SERVER_ERROR);
  private final String errorMessage;
  private final HttpStatus httpStatus;

  Error(final String errorMessage, final HttpStatus httpStatus) {
    this.errorMessage = errorMessage;
    this.httpStatus = httpStatus;
  }
}
