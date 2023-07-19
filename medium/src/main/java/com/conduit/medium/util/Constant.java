package com.conduit.medium.util;

/**
 * class to hold application constants.
 */
public class Constant {

  public static final String RECENT_ARTICLES_QUERY = """
      SELECT a FROM articles a
      WHERE
      (:tag IS NULL
          OR a.article_id IN (
              SELECT tta.article_id FROM tags_to_articles tta WHERE tta.tag_id = (
                  SELECT tag_id FROM tags WHERE name = :tag
              )
          )
      )
      AND
      (:author IS NULL
          OR a.user_id IN (
              SELECT user_id FROM users WHERE user_name = :author
          )
      )
      AND
      (:favorited IS NULL
          OR a.user_id IN (
              SELECT user_id FROM users WHERE user_name = :favorited
          )
      )
      ORDER BY a.created_at DESC
      """;

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
