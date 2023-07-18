package com.conduit.medium.dto.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * data class to add comments to a article.
 */
@JsonTypeName("comment")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public record AddCommentRequest(String body) {
}
