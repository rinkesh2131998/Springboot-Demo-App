package com.conduit.medium.util;

/**
 * class to hold application constants.
 */
public class Constant {

  public static final String USERNAME_NOT_FOUND_MESSAGE =
      "Unable to fetch user id for the user requesting creation of article";

  private Constant() {
    throw new IllegalStateException("Utility classes should not be instantiated.");
  }

  public static class Auth {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Token";

    private Auth() {
      throw new IllegalStateException("Utility classes should not be instantiated.");
    }

  }
}
