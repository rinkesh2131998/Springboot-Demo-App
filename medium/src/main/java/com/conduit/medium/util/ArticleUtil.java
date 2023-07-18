package com.conduit.medium.util;

import com.conduit.medium.dto.article.AddCommentRequest;
import com.conduit.medium.dto.article.CreateArticleRequest;
import com.conduit.medium.model.entity.Article;
import com.conduit.medium.model.entity.Comment;
import com.conduit.medium.model.entity.User;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * utility class for article service.
 */
public class ArticleUtil {

  private ArticleUtil() {
    throw new IllegalStateException("Static class should not be instantiated");
  }

  /**
   * get the slug for an article using it's title.
   *
   * @param title to generate slug from
   * @return slug of the article
   */
  public static String getSlugFromTitle(final String title) {
    return title.toLowerCase().replace(" ", "-");
  }

  /**
   * convert an article creation request to it's corresponding article entity class.
   *
   * @param articleRequest used for creating a new article
   * @param userId         author info of the article
   * @return Article entity object to be saved to db
   */
  public static Article articleDtoToEntity(final CreateArticleRequest articleRequest,
                                           final UUID userId) {
    final Article article = new Article();
    article.setSlug(ArticleUtil.getSlugFromTitle(articleRequest.title()));
    article.setTitle(articleRequest.title());
    article.setDescription(articleRequest.description());
    article.setBody(articleRequest.body());
    article.setUserId(userId);
    article.setCreatedAt(LocalDateTime.now());
    return article;
  }

  /**
   * used to create a comment entity to be saved using the request args.
   *
   * @return comment object
   */
  public static Comment createCommentEntity(final AddCommentRequest addCommentRequest,
                                            final Optional<Article> optionalArticle,
                                            final Optional<User> byUserName) {
    final Comment comment = new Comment();
    comment.setArticleId(optionalArticle.get().getArticleId());
    comment.setUserId(byUserName.get().getUserId());
    comment.setBody(addCommentRequest.body());
    comment.setCreatedAt(LocalDateTime.now());
    return comment;
  }

}
