package com.conduit.medium.service.article;

import com.conduit.medium.dto.article.AddCommentRequest;
import com.conduit.medium.dto.article.ArticleResponse;
import com.conduit.medium.dto.article.CreateArticleRequest;
import com.conduit.medium.dto.article.MultipleCommentResponse;
import com.conduit.medium.dto.article.SingleCommentResponse;
import com.conduit.medium.dto.article.UpdateArticle;
import com.conduit.medium.security.service.UserDetailsImpl;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * service to have article's related business logic.
 */
@Service
public interface ArticleService {

  /**
   * create a new article.
   *
   * @param userDetails    used for getting the author details
   * @param articleRequest payload used to create new article.
   * @return complete article detail object
   */
  ArticleResponse createArticle(UserDetailsImpl userDetails, CreateArticleRequest articleRequest);

  /**
   * fetch a article with the given slug.
   *
   * @param userDetails requesting user
   * @param slug        used to fetch the article
   * @return article details
   */
  ArticleResponse getArticle(UserDetailsImpl userDetails, String slug);

  /**
   * update article with the given slug.
   *
   * @param userDetails   to validate the author of the article is requesting to update the article
   * @param slug          to fetch teh article
   * @param updateArticle fields to update in the article
   * @return updated article
   */
  ArticleResponse updateArticle(UserDetailsImpl userDetails, String slug,
                                UpdateArticle updateArticle);

  /**
   * delete a article, only if it's author requests.
   *
   * @param userDetails to verify the author of article
   * @param slug        used to fetch the article
   */
  void deleteArticle(UserDetailsImpl userDetails, String slug);

  /**
   * get the most recent articles.
   *
   * @param limit     number of articles to return
   * @param offset    to applied to limit
   * @param tag       optional filter
   * @param author    optional filter
   * @param favorited optional filter
   * @return most recent articles.
   */
  List<ArticleResponse> getMostRecentArticles(long limit, long offset, String tag, String author,
                                              String favorited);

  /**
   * get feed articles of a user
   *
   * @param userDetails for which articles to fetch
   * @param limit       for pagination
   * @param offset      for pagination
   * @return articles by a user
   */
  List<ArticleResponse> getFeedArticles(UserDetailsImpl userDetails, long limit, long offset);

  /**
   * add new comments to articles.
   *
   * @param userDetails       to fetch the comment author
   * @param slug              to fetch the article to add comment to
   * @param addCommentRequest comment to add
   * @return new created comment with author
   */
  SingleCommentResponse addCommentToArticle(UserDetailsImpl userDetails, String slug,
                                            AddCommentRequest addCommentRequest);

  /**
   * return all comments for this article.
   *
   * @param slug to fetch the article
   * @return all comments
   */
  MultipleCommentResponse getAllComments(String slug);

  /**
   * delete a comment using its id for the given slug
   *
   * @param slug to get the article info
   * @param id   of the comment
   */
  void deleteCommentForArticle(String slug, long id);

  /**
   * favorite an article for the given user.
   *
   * @param userDetails to fetch user details
   * @param slug        article to be favorited
   */
  ArticleResponse favouriteArticle(UserDetailsImpl userDetails, String slug);

  /**
   * un favourite an article for the given user.
   *
   * @param userDetails to fetch user details
   * @param slug        article to un-favorite
   */
  ArticleResponse unFavouriteArticle(UserDetailsImpl userDetails, String slug);
}
