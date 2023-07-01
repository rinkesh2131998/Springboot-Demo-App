package com.conduit.medium.service.article;

import com.conduit.medium.dto.article.ArticleResponse;
import com.conduit.medium.dto.article.CreateArticleRequest;
import com.conduit.medium.security.service.UserDetailsImpl;
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
}
