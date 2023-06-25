package com.conduit.medium.controller;

import com.conduit.medium.dto.profile.ProfileResponse;
import com.conduit.medium.security.service.UserDetailsImpl;
import com.conduit.medium.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * endpoints for user profiles.
 */
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
  private final ProfileService profileService;

  @GetMapping("/{username}")
  public ProfileResponse getProfile(@AuthenticationPrincipal final UserDetailsImpl userDetails,
                                    @PathVariable final String username) {
    return profileService.getProfile(userDetails, username);
  }

  @PostMapping("/{username}/follow")
  public ProfileResponse followUser(@AuthenticationPrincipal final UserDetailsImpl userDetails,
                                    @PathVariable final String username) {
    return profileService.followUser(userDetails, username);
  }

  @DeleteMapping("/{username}/follow")
  public ProfileResponse unfollowUser(@AuthenticationPrincipal final UserDetailsImpl userDetails,
                                      @PathVariable final String username) {
    return profileService.unfollowUser(userDetails, username);
  }
}
