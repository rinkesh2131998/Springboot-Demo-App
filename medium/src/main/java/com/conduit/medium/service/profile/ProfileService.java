package com.conduit.medium.service.profile;

import com.conduit.medium.dto.profile.ProfileResponse;
import com.conduit.medium.security.service.UserDetailsImpl;
import org.springframework.stereotype.Service;

/**
 * service for methods related to viewing user profiles,
 * following, unfollowing user.
 */
@Service
public interface ProfileService {

  /**
   * get the profile of a user.
   *
   * @param userDetails used to get the user requesting the profile
   * @param username    used to fetch the profile
   * @return profile of the user with the given username
   */
  ProfileResponse getProfile(UserDetailsImpl userDetails, String username);

  /**
   * follow the user with given username.
   *
   * @param userDetails to get the user making the follow request
   * @param username    to follow
   * @return profile of followed user
   */
  ProfileResponse followUser(UserDetailsImpl userDetails, String username);

  /**
   * unfollow the user with given username.
   *
   * @param userDetails to get the user making the unfollow request
   * @param username    to unfollow
   * @return profile of followed user
   */
  ProfileResponse unfollowUser(UserDetailsImpl userDetails, String username);
}
