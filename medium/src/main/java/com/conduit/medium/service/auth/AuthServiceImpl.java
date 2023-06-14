package com.conduit.medium.service.auth;

import com.conduit.medium.dto.user.LoginRequest;
import com.conduit.medium.dto.user.UserRegisterRequest;
import com.conduit.medium.dto.user.UserResponse;
import com.conduit.medium.enums.Error;
import com.conduit.medium.exception.ApplicationException;
import com.conduit.medium.model.entity.User;
import com.conduit.medium.repository.UserRepository;
import com.conduit.medium.security.jwt.JwtUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Data
@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserResponse registerUser(final UserRegisterRequest payload) {
    log.info("Registering new user for username: [{}], email: [{}]", payload.username(),
        payload.email());
    final boolean userNameExists = userRepository.existsByUserName(payload.username());
    if (userNameExists) {
      log.error("Unable to register user, cause: User with user name: [{}], already exists",
          payload.username());
      throw new ApplicationException(Error.USERNAME_EXISTS_EXCEPTION);
    }
    final boolean emailsExists = userRepository.existsByEmail(payload.email());
    if (emailsExists) {
      log.error("Unable to register user, cause: User with email: [{}], already exists",
          payload.email());
      throw new ApplicationException(Error.EMAIL_EXISTS_EXCEPTION);
    }
    log.debug("Registering a new user with user name: [{}], and email: [{}]", payload.username(),
        payload.email());
    final User user =
        new User(payload.username(), payload.email(), passwordEncoder.encode(payload.password()),
            null, null, LocalDateTime.now(), null);
    userRepository.save(user);
    return UserResponse.builder().username(user.getUserName())
        .email(user.getEmail()).build();
  }

  @Override
  public UserResponse loginUser(final LoginRequest payload) {
    log.info("Login in user with email: [{}]", payload.email());
    final Optional<User> user = userRepository.findByEmail(payload.email());
    if (user.isEmpty()) {
      log.error("Unable to login as no user with given email: [{}] exists", payload.email());
      throw new ApplicationException(Error.EMAIL_NOT_EXIST);
    }
    if (!passwordEncoder.matches(payload.password(), user.get().getPassword())) {
      log.error("Unable to login, cause: invalid credentials");
      throw new ApplicationException(Error.INVALID_PASSWORD);
    }
    final Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.email(),
            payload.password()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String jwtToken = jwtUtil.generateJwtToken(authentication);
    log.debug("Creating response object with generated jwt for user: [{}]",
        payload.email());
    return UserResponse.builder()
        .username(user.get().getUserName())
        .email(user.get().getEmail())
        .token(jwtToken)
        .bio(user.get().getBio())
        .image(user.get().getImage())
        .build();
  }
}
