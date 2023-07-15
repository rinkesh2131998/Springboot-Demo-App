package com.conduit.medium.security.service;

import com.conduit.medium.enums.Error;
import com.conduit.medium.exception.ApplicationException;
import com.conduit.medium.model.entity.User;
import com.conduit.medium.repository.UserRepository;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * service used for jwt token authentication.
 */
@Slf4j
@Data
@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final Optional<User> user = userRepository.findByEmail(username);
    log.debug("Found user with username: [{}]", username);
    if (user.isEmpty()) {
      throw new ApplicationException(Error.USERNAME_NOT_FOUND_EXCEPTION);
    }
    return UserDetailsImpl.build(user.get());
  }
}
