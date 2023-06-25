package com.conduit.medium.dto.user;

import lombok.Builder;

/**
 * dto used for updating users.
 */
@Builder
public record UserUpdateRequest(String username, String email,
                                String password, String image, String bio) {
}
