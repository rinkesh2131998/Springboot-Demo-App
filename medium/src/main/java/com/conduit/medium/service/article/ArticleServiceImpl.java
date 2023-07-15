package com.conduit.medium.service.article;

import com.conduit.medium.dto.article.ArticleResponse;
import com.conduit.medium.dto.article.CreateArticleRequest;
import com.conduit.medium.dto.article.UpdateArticle;
import com.conduit.medium.dto.profile.ProfileResponse;
import com.conduit.medium.enums.Error;
import com.conduit.medium.exception.ApplicationException;
import com.conduit.medium.model.entity.Article;
import com.conduit.medium.model.entity.Tag;
import com.conduit.medium.model.entity.TagToArticle;
import com.conduit.medium.model.entity.User;
import com.conduit.medium.repository.ArticleRepository;
import com.conduit.medium.repository.FavouriteRepository;
import com.conduit.medium.repository.TagToArticleRepository;
import com.conduit.medium.repository.UserRepository;
import com.conduit.medium.security.service.UserDetailsImpl;
import com.conduit.medium.service.profile.ProfileService;
import com.conduit.medium.service.tag.TagService;
import com.conduit.medium.util.ArticleUtil;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * implementation of articleService interface.
 */
@Slf4j
@Data
@Service
public class ArticleServiceImpl implements ArticleService {

  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;
  private final TagService tagService;
  private final ProfileService profileService;
  private final TagToArticleRepository toArticleRepository;
  private final FavouriteRepository favouriteRepository;

  @Override
  // @Transactional(propagation = Propagation.REQUIRES_NEW)
  // TODO: 7/15/23 think about how to implement proper transactions here
  public ArticleResponse createArticle(final UserDetailsImpl userDetails,
                                       final CreateArticleRequest articleRequest) {
    log.info("Creating new article with title: [{}]", articleRequest.title());
    final Optional<User> byUserName =
        userRepository.findByUserName(userDetails.getUsername());
    if (byUserName.isEmpty()) {
      log.debug("Unable to fetch user id for the user requesting creation of article");
      throw new ApplicationException(Error.USERNAME_NOT_FOUND_EXCEPTION);
    }
    try {
      final Article article =
          articleRepository.save(ArticleUtil.articleDtoToEntity(articleRequest,
              byUserName.get().getUserId()));
      log.info("Saved article with id: [{}] to db, returning article response",
          article.getArticleId());
      saveNewTags(articleRequest, article.getArticleId());
      return toArticleResponseDto(byUserName.get().getUserId(), article);
    } catch (final Exception exception) {
      log.debug("Unable to create new article, cause: [{}]", exception.getMessage());
      throw new ApplicationException(Error.ARTICLE_NOT_CREATED_EXCEPTION);
    }
  }

  @Override
  public ArticleResponse getArticle(final UserDetailsImpl userDetails, final String slug) {
    final Optional<Article> articleBySlug = articleRepository.findBySlug(slug);
    if (articleBySlug.isEmpty()) {
      throw new ApplicationException(Error.ARTICLE_NOT_FOUND_EXCEPTION);
    }
    final Optional<User> byUserName =
        userRepository.findByUserName(userDetails.getUsername());
    if (byUserName.isEmpty()) {
      log.debug("Unable to fetch user id for the user requesting creation of article");
      throw new ApplicationException(Error.USERNAME_NOT_FOUND_EXCEPTION);
    }
    return toArticleResponseDto(byUserName.get().getUserId(), articleBySlug.get());
  }

  @Override
  @Transactional
  public ArticleResponse updateArticle(final UserDetailsImpl userDetails, final String slug,
                                       final UpdateArticle updateArticle) {
    log.info("Updating article with slug: [{}]", slug);
    final Optional<Article> optionalArticle = articleRepository.findBySlug(slug);
    if (optionalArticle.isEmpty()) {
      log.error("Unable to find article to update");
      throw new ApplicationException(Error.ARTICLE_NOT_FOUND_EXCEPTION);
    }
    final Optional<User> byUserName =
        userRepository.findByUserName(userDetails.getUsername());
    if (byUserName.isEmpty() ||
        !byUserName.get().getUserId().equals(optionalArticle.get().getUserId())) {
      log.error("Unable to update article as the user requesting update is not author of article");
      throw new ApplicationException(Error.ARTICLE_AUTHOR_INVALID_EXCEPTION);
    }
    log.debug("Checking articles fields to update and updating accordingly");
    if (Objects.nonNull(updateArticle.title())) {
      final String slugFromTitle = ArticleUtil.getSlugFromTitle(updateArticle.title());
      final Optional<Article> articleRepositoryBySlug = articleRepository.findBySlug(slugFromTitle);
      if (articleRepositoryBySlug.isPresent()) {
        log.error("Article with new updated title: [{}] and corresponding slug: [{}], already "
            + "present, unable to update article", updateArticle.title(), slugFromTitle);
        throw new ApplicationException(Error.ARTICLE_TITLE_ALREADY_EXISTS_EXCEPTION);
      }
      optionalArticle.get().setTitle(updateArticle.title());
      optionalArticle.get().setSlug(slugFromTitle);
    }
    if (Objects.nonNull(updateArticle.description())) {
      optionalArticle.get().setDescription(updateArticle.description());
    }
    if (Objects.nonNull(updateArticle.body())) {
      optionalArticle.get().setBody(updateArticle.body());
    }
    final Article updatedArticle = articleRepository.save(optionalArticle.get());
    log.debug("Updated article with slug: [{}]", slug);
    return toArticleResponseDto(byUserName.get().getUserId(), updatedArticle);
  }

  @Override
  public void deleteArticle(final UserDetailsImpl userDetails, final String slug) {
    log.info("Validating user request to delete article with slug: [{}]", slug);
    try {
      final Optional<Article> optionalArticle = articleRepository.findBySlug(slug);
      if (optionalArticle.isEmpty()) {
        throw new ApplicationException(Error.ARTICLE_NOT_FOUND_EXCEPTION);
      }
      final Optional<User> byUserName =
          userRepository.findByUserName(userDetails.getUsername());
      if (byUserName.isEmpty() ||
          !byUserName.get().getUserId().equals(optionalArticle.get().getUserId())) {
        throw new ApplicationException(Error.ARTICLE_AUTHOR_INVALID_EXCEPTION);
      }
      log.debug("Deleting article with slug: [{}]", slug);
      articleRepository.delete(optionalArticle.get());
    } catch (final Exception exception) {
      log.error("Unable to delete article, cause: [{}]", exception.getMessage());
      throw new ApplicationException(Error.ARTICLE_DELETE_EXCEPTION);
    }
  }

  private void saveNewTags(final CreateArticleRequest articleRequest, final UUID articleId) {
    log.debug("Mapping any tagList needed to article: [{}]", articleId);
    if (Objects.isNull(articleRequest.tagList())) {
      log.debug("No tagList present to add to this article");
      return;
    }
    final List<Tag> tags = tagService.addTag(articleRequest.tagList());
    log.debug("Added new tagList to db");
    tags.parallelStream().map(tag -> {
      final TagToArticle tagToArticle = new TagToArticle();
      tagToArticle.setArticleId(articleId);
      tagToArticle.setTagId(tag.getTagId());
      return tagToArticle;
    }).forEach(toArticleRepository::save);
    log.debug("Mapped tagList to new articles.");
  }

  private ArticleResponse toArticleResponseDto(UUID userId, Article article) {

    log.debug("Fetching article author for article id: [{}]", article.getArticleId());
    final Optional<User> userFromUserId = getUserFromUserId(article.getUserId());
    final ProfileResponse profileResponse =
        userFromUserId.map(user -> profileService.getProfile(userId, user.getUserId()))
            .orElse(null);
    final ArticleResponse.Author author = ArticleResponse.Author.builder()
        .profileResponse(profileResponse).build();
    return ArticleResponse.builder()
        .slug(article.getSlug())
        .title(article.getTitle())
        .description(article.getDescription())
        .body(article.getBody())
        .tagList(tagService.getTags(article.getArticleId()))
        .createdAt(article.getCreatedAt())
        .updatedAt(article.getUpdatedAt())
        .favorited(favouriteRepository.existsByUserIdAndArticleId(userId, article.getArticleId()))
        .favoritesCount(favouriteRepository.countByArticleId(article.getArticleId()))
        .author(author).build();
  }

  private Optional<User> getUserFromUserId(final UUID userId) {
    return userRepository.findById(userId);
  }
}
