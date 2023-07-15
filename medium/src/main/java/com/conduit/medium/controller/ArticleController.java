package com.conduit.medium.controller;

import com.conduit.medium.dto.article.ArticleResponse;
import com.conduit.medium.dto.article.CreateArticleRequest;
import com.conduit.medium.dto.article.UpdateArticle;
import com.conduit.medium.security.service.UserDetailsImpl;
import com.conduit.medium.service.article.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller for article related endpoints.
 */
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

  private final ArticleService articleService;

  /**
   * add a new article with this endpoint.
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ArticleResponse> createArticle(
      @AuthenticationPrincipal final UserDetailsImpl userDetails,
      @RequestBody CreateArticleRequest articleRequest) {
    final ArticleResponse article = articleService.createArticle(userDetails, articleRequest);
    return ResponseEntity.ok(article);
  }

  /**
   * get an article by slug.
   */
  @GetMapping("/{slug}")
  public ResponseEntity<ArticleResponse> getArticlesBySlug(
      @AuthenticationPrincipal final UserDetailsImpl userDetails,
      @PathVariable final String slug) {
    final ArticleResponse article = articleService.getArticle(userDetails, slug);
    return ResponseEntity.ok(article);
  }

  /**
   * update article's title, body, description.
   */
  @PutMapping("/{slug}")
  public ResponseEntity<ArticleResponse> updateArticlesBySlug(
      @AuthenticationPrincipal final UserDetailsImpl userDetails, @PathVariable final String slug,
      @RequestBody final UpdateArticle updateArticle) {
    final ArticleResponse articleResponse =
        articleService.updateArticle(userDetails, slug, updateArticle);
    return ResponseEntity.ok(articleResponse);
  }

  /**
   * delete a article with the given slug.
   */
  @DeleteMapping("/{slug}")
  public ResponseEntity<Void> deleteArticleBySlug(
      @AuthenticationPrincipal final UserDetailsImpl userDetails,
      @PathVariable final String slug) {
    articleService.deleteArticle(userDetails, slug);
    return ResponseEntity.ok().build();
  }

}