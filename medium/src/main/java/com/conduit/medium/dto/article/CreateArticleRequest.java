package com.conduit.medium.dto.article;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

/**
 * request dto to create a article.
 */
@Builder
public record CreateArticleRequest(@NotNull String title, @NotNull String description,
                                   @NotNull String body,
                                   List<String> tags) {
}
