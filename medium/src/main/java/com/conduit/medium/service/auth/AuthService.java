package com.conduit.medium.service.auth;

import com.conduit.medium.dto.user.LoginRequest;
import com.conduit.medium.dto.user.UserRegisterRequest;
import com.conduit.medium.dto.user.UserResponse;
import org.springframework.stereotype.Service;

/**
 * service used for registering new users and login them in.
 */
@Service
public interface AuthService {

  /**
   * used to register a new user.
   *
   * @param payload user registration data
   * @return user on creation successfully
   */
  UserResponse registerUser(UserRegisterRequest payload);

  /**
   * used to sing in a user.
   *
   * @param payload used for authentication
   * @return user on login successfully
   */
  UserResponse loginUser(LoginRequest payload);
}
