package com.conduit.medium.dto.article;

import com.conduit.medium.dto.profile.ProfileResponse;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

/**
 * dto for single article response object.
 */
@JsonTypeName("article")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@Builder
public record ArticleResponse(String slug, String title, String description, String body,
                              List<String> tagList, LocalDateTime createdAt,
                              LocalDateTime updatedAt, boolean favorited, long favoritesCount,
                              com.conduit.medium.dto.article.ArticleResponse.Author author) {
  @Builder
  public record Author(ProfileResponse profileResponse) {
  }
}
