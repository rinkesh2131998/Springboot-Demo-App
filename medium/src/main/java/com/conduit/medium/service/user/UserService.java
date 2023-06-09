package com.conduit.medium.service.user;

import com.conduit.medium.dto.user.UserResponse;
import com.conduit.medium.dto.user.UserUpdateRequest;
import com.conduit.medium.security.service.UserDetailsImpl;
import org.springframework.stereotype.Service;

/**
 * user service to get current user and update user data.
 */
@Service
public interface UserService {

  /**
   * fetch teh current user using authentication details of that user.
   *
   * @param payload containing user data to fetch the current user from db
   * @return all user details for the current user
   */
  UserResponse getCurrentUser(UserDetailsImpl payload);

  /**
   * update user details.
   *
   * @param userDetails used to get the current user for which the update needs to be done
   * @param payload     containing fields that can be updated
   * @return user object in case of successfully update
   */
  UserResponse updateUser(UserDetailsImpl userDetails, UserUpdateRequest payload);
}
