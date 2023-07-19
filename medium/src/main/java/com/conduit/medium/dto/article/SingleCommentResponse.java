package com.conduit.medium.dto.article;

import lombok.Builder;

/**
 * data class to return single comments for an article.
 */
@Builder
public record SingleCommentResponse(CommentResponse comment) {
}
