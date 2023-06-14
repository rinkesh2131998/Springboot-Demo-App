package com.conduit.medium.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * data class to hold the login request from a user.
 */
public record LoginRequest(@NotNull @Email String email, @NotNull @NotBlank
@Size(min = 8, max = 32, message = "password must be between 8 to 32 chars")
String password) {
}
