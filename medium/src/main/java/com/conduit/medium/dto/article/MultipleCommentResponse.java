package com.conduit.medium.dto.article;

import java.util.List;
import lombok.Builder;

/**
 * data class to return all comments for an article.
 */
@Builder
public record MultipleCommentResponse(List<CommentResponse> commentResponses) {
}
