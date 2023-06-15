package com.conduit.medium.service.user;

import com.conduit.medium.dto.user.UserResponse;
import com.conduit.medium.enums.Error;
import com.conduit.medium.exception.ApplicationException;
import com.conduit.medium.model.entity.User;
import com.conduit.medium.repository.UserRepository;
import com.conduit.medium.security.service.UserDetailsImpl;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * implementation of the UserService interface.
 */
@Slf4j
@Data
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserResponse getCurrentUser(final UserDetailsImpl payload) {
    log.info("Fetching user with username: [{}]", payload.getUsername());
    final Optional<User> byUserName =
        userRepository.findByUserName(payload.getUsername());
    if (byUserName.isEmpty()) {
      log.error("Unable to find user with username: [{}]", payload.getUsername());
      throw new ApplicationException(Error.USERNAME_NOT_FOUND_EXCEPTION);
    }
    return UserResponse.builder()
        .username(byUserName.get().getUserName())
        .email(byUserName.get().getEmail())
        .bio(byUserName.get().getBio())
        .image(byUserName.get().getImage())
        .build();
  }

}
