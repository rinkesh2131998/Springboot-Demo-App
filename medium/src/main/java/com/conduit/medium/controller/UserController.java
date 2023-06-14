package com.conduit.medium.controller;

import com.conduit.medium.dto.user.LoginRequest;
import com.conduit.medium.dto.user.UserRegisterRequest;
import com.conduit.medium.dto.user.UserResponse;
import com.conduit.medium.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * endpoints related to registering, logging users and updating them.
 */
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
  private final AuthService authService;

  @PostMapping()
  public UserResponse createUser(@RequestBody UserRegisterRequest payload) {
    return authService.registerUser(payload);
  }

  @PostMapping("/login")
  public UserResponse login(@RequestBody LoginRequest payload) {
    return authService.loginUser(payload);
  }
}
