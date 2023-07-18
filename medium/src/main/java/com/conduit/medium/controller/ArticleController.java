package com.conduit.medium.controller;

import com.conduit.medium.dto.article.AddCommentRequest;
import com.conduit.medium.dto.article.ArticleResponse;
import com.conduit.medium.dto.article.CommentResponse;
import com.conduit.medium.dto.article.CreateArticleRequest;
import com.conduit.medium.dto.article.MultipleCommentResponse;
import com.conduit.medium.dto.article.UpdateArticle;
import com.conduit.medium.security.service.UserDetailsImpl;
import com.conduit.medium.service.article.ArticleService;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
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

  /**
   * get list of all the most recent articles
   *
   * @return list of most recent articles wrt the given limit and offset
   */
  @GetMapping()
  public ResponseEntity<List<ArticleResponse>> getArticles(
      @RequestParam(defaultValue = "20") final long limit,
      @RequestParam(defaultValue = "0") final long offset,
      @RequestParam(required = false) final String tag,
      @RequestParam(required = false) final String author,
      @RequestParam(required = false) final String favorited
  ) {
    final List<ArticleResponse> mostRecentArticles =
        articleService.getMostRecentArticles(limit, offset, tag, author, favorited);
    return ResponseEntity.ok(mostRecentArticles);
  }

  /**
   * add new comments to an article.
   */
  @PostMapping("/{slug}/comments")
  public ResponseEntity<CommentResponse> addComments(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable final String slug,
      @RequestBody final
      AddCommentRequest addCommentRequest) {
    final CommentResponse commentResponse =
        articleService.addCommentToArticle(userDetails, slug, addCommentRequest);
    return ResponseEntity.ok(commentResponse);
  }

  /**
   * add comment to an article
   */
  @GetMapping("/{slug}/comments")
  public ResponseEntity<MultipleCommentResponse> getAllCommentsByArticle(
      @PathVariable final String slug) {
    final MultipleCommentResponse allComments = articleService.getAllComments(slug);
    return ResponseEntity.ok(allComments);
  }

  /**
   * delete a comment from an article
   */
  @DeleteMapping("/{slug}/comments/{:id}")
  public ResponseEntity<Void> deleteComment(@PathVariable final String slug,
                                            @PathVariable final long id) {
    articleService.deleteCommentForArticle(slug, id);
    return ResponseEntity.ok().build();
  }

  /**
   * favorite an article
   */
  @PostMapping("/{slug}/favorite")
  public ResponseEntity<Void> favoriteArticle(
      @AuthenticationPrincipal final UserDetailsImpl userDetails, @PathVariable final String slug) {
    articleService.favouriteArticle(userDetails, slug);
    return ResponseEntity.ok().build();
  }

  /**
   * un-favorite and article.
   */
  @DeleteMapping("/{slug}/favorite")
  public ResponseEntity<Void> unFavoriteArticle(
      @AuthenticationPrincipal final UserDetailsImpl userDetails, @PathVariable final String slug) {
    articleService.unFavouriteArticle(userDetails, slug);
    return ResponseEntity.ok().build();
  }
}
