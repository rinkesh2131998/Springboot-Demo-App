package com.conduit.medium.service.user;

import com.conduit.medium.dto.user.UserResponse;
import com.conduit.medium.dto.user.UserUpdateRequest;
import com.conduit.medium.enums.Error;
import com.conduit.medium.exception.ApplicationException;
import com.conduit.medium.model.entity.User;
import com.conduit.medium.repository.UserRepository;
import com.conduit.medium.security.service.UserDetailsImpl;
import java.util.Objects;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * implementation of the UserService interface.
 */
@Slf4j
@Data
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;


  @Override
  public UserResponse getCurrentUser(final UserDetailsImpl payload) {
    log.info("Fetching user with username: [{}]", payload.getUsername());
    final User byUserName =
        validateUserExists(payload);
    return UserResponse.builder()
        .username(byUserName.getUserName())
        .email(byUserName.getEmail())
        .bio(byUserName.getBio())
        .image(byUserName.getImage())
        .build();
  }

  @Override
  public UserResponse updateUser(final UserDetailsImpl userDetails,
                                 final UserUpdateRequest payload) {
    log.info("Updating user details for username: [{}]", userDetails.getUsername());
    final User user = validateUserExists(userDetails);
    setUpdatedValues(user, payload);
    userRepository.save(user);
    log.info("Updated user with id: [{}]", user.getUserId());
    return UserResponse.builder()
        .username(user.getUserName())
        .email(user.getEmail())
        .bio(user.getBio())
        .image(user.getImage())
        .build();
  }

  private void setUpdatedValues(final User user, final UserUpdateRequest payload) {
    if (validateField(payload.username())) {
      user.setUserName(payload.username());
    }
    if (validateField(payload.email())) {
      user.setEmail(payload.email());
    }
    if (validateField(payload.password())) {
      user.setPassword(passwordEncoder.encode(payload.password()));
    }
    if (validateField(payload.image())) {
      user.setImage(payload.image());
    }
    if (validateField(payload.bio())) {
      user.setBio(payload.bio());
    }
  }

  private User validateUserExists(final UserDetailsImpl userDetails) {
    final Optional<User> byUserName =
        userRepository.findByUserName(userDetails.getUsername());
    if (byUserName.isEmpty()) {
      log.error("Unable to find user with username: [{}]", userDetails.getUsername());
      throw new ApplicationException(Error.USERNAME_NOT_FOUND_EXCEPTION);
    }
    return byUserName.get();
  }

  private boolean validateField(final String field) {
    return Objects.nonNull(field) && !field.equals("") && !field.equals(" ");
  }
}
