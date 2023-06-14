package com.conduit.medium.dto.user;

import lombok.Builder;

/**
 * data class to return a user with a jwt token.
 */
@Builder
public record UserResponse(String email, String token, String username, String bio, String image) {
}
