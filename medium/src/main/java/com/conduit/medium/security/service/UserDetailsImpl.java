package com.conduit.medium.security.service;

import com.conduit.medium.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * class to fetch user details.
 */
@Slf4j
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1382942L;

  private final UUID userId;
  private final String userName;
  @Getter
  private final String email;

  @JsonIgnore
  private final String password;

  private final Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(final UUID userId, final String userName, final String email,
                         final String password,
                         final Collection<? extends GrantedAuthority> authorities) {
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    return new UserDetailsImpl(user.getUserId(), user.getUserName(), user.getEmail(),
        user.getPassword(), Collections.emptyList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }
    if (Objects.isNull(object) || getClass() != object.getClass()) {
      return false;
    }
    final UserDetailsImpl userDetails = (UserDetailsImpl) object;
    return Objects.equals(userId, userDetails.userId);
  }
}
