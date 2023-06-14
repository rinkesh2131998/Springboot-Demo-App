package com.conduit.medium.dto.user;

import lombok.Builder;

/**
 * data class for profile lookup response.
 */
@Builder
public record ProfileResponse(String email, String token, String username, String bio,
                              String image) {
}
