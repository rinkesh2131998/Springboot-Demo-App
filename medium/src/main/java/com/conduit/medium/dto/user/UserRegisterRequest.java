package com.conduit.medium.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * data class for user registration request.
 */
public record UserRegisterRequest(@NotNull String username, @NotNull @Email String email,
                                  @NotNull @Size(min = 8, max = 32, message = "password must be between 8 to 32 chars")
                                  String password) {
}
