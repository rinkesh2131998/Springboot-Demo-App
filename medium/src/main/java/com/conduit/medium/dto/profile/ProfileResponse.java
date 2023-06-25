package com.conduit.medium.dto.profile;

import lombok.Builder;

/**
 * data class for profile lookup response.
 */
@Builder
public record ProfileResponse(String username, String bio, String image, boolean following) {
}
