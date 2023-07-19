package com.conduit.medium.controller;

import com.conduit.medium.dto.user.LoginRequest;
import com.conduit.medium.dto.user.UserRegisterRequest;
import com.conduit.medium.dto.user.UserResponse;
import com.conduit.medium.dto.user.UserUpdateRequest;
import com.conduit.medium.security.service.UserDetailsImpl;
import com.conduit.medium.service.auth.AuthService;
import com.conduit.medium.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api")
public class UserController {
  private final AuthService authService;
  private final UserService userService;

  @PostMapping("/users")
  public UserResponse createUser(@RequestBody final UserRegisterRequest payload) {
    return authService.registerUser(payload);
  }

  @PostMapping("/users/login")
  public UserResponse login(@RequestBody final LoginRequest payload) {
    return authService.loginUser(payload);
  }

  @GetMapping("/user")
  public UserResponse getUser(@AuthenticationPrincipal final UserDetailsImpl payload) {
    return userService.getCurrentUser(payload);
  }

  @PutMapping("/user")
  public UserResponse updateUser(@AuthenticationPrincipal final UserDetailsImpl authPrinciple,
                                 @RequestBody final UserUpdateRequest payload) {
    return userService.updateUser(authPrinciple, payload);
  }
}
