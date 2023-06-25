package com.conduit.medium.dto.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;

/**
 * dto used for updating users.
 */
@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@Builder
public record UserUpdateRequest(String username, String email,
                                String password, String image, String bio) {
}
