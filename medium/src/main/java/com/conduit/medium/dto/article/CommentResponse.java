package com.conduit.medium.dto.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDateTime;
import lombok.Builder;

/**
 * data class to return added comments.
 */
@JsonTypeName("comment")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@Builder
public record CommentResponse(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                              String body, ArticleResponse.Author author) {
}
