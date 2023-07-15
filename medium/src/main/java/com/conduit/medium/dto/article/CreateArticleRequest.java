package com.conduit.medium.dto.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

/**
 * request dto to create a article.
 */
@JsonTypeName("article")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@Builder
public record CreateArticleRequest(@NotNull String title, @NotNull String description,
                                   @NotNull String body,
                                   List<String> tagList) {
}
