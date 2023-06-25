package com.conduit.medium.dto.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * data class for user registration request.
 */
@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public record UserRegisterRequest(@NotNull @Pattern(regexp = "^[a-zA-Z0-9._-]{7,}$", message =
    "username should be greater than 7 characters") String username,
                                  @NotNull @Email String email,
                                  @NotNull @Size(min = 8, max = 32, message = "password must be between 8 to 32 chars")
                                  String password) {
}
