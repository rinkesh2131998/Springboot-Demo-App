package com.conduit.medium.dto.article;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * data class to return added comments.
 */
@Builder
public record CommentResponse(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                              String body, ArticleResponse.Author author) {
}
