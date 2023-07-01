package com.conduit.medium.service.profile;

import com.conduit.medium.dto.profile.ProfileResponse;
import com.conduit.medium.enums.Error;
import com.conduit.medium.exception.ApplicationException;
import com.conduit.medium.model.entity.Follower;
import com.conduit.medium.model.entity.User;
import com.conduit.medium.repository.FollowerRepository;
import com.conduit.medium.repository.UserRepository;
import com.conduit.medium.security.service.UserDetailsImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * implementation of profileService interface.
 */
@Slf4j
@Data
@Service
public class ProfileServiceImpl implements ProfileService {

  private final UserRepository userRepository;
  private final FollowerRepository followerRepository;

  private static ProfileResponse toProfileResponseDto(final Optional<User> followed,
                                                      final boolean isFollowing) {
    return ProfileResponse.builder()
        .username(followed.get().getUserName())
        .image(followed.get().getImage())
        .bio(followed.get().getBio())
        .following(isFollowing).build();
  }

  @Override
  public ProfileResponse getProfile(final UserDetailsImpl userDetails, final String username) {
    log.info("Fetching profiles for user: [{}]", username);
    final Optional<User> followed = userRepository.findByUserName(username);
    validateIfUserExists(username, followed.isEmpty());
    final Optional<User> follower = userRepository.findByUserName(userDetails.getUsername());
    validateIfUserExists(userDetails.getUsername(), follower.isEmpty());
    final Optional<Follower> byFollowerIdAndFollowedId =
        followerRepository.findByFollowerIdAndFollowedId(follower.get().getUserId(),
            followed.get().getUserId());
    final boolean isFollowing = byFollowerIdAndFollowedId.isPresent();
    log.info("Fetched user profile for: [{}]", username);
    return toProfileResponseDto(followed, isFollowing);
  }

  @Override
  public ProfileResponse getProfile(final UUID requestingUserId, final UUID userId) {
    log.info("Fetching profiles for user: [{}]", userId);
    final Optional<User> followed = userRepository.findById(userId);
    validateIfUserExists(userId.toString(), followed.isEmpty());
    final Optional<User> follower = userRepository.findById(requestingUserId);
    validateIfUserExists(requestingUserId.toString(), follower.isEmpty());
    final Optional<Follower> byFollowerIdAndFollowedId =
        followerRepository.findByFollowerIdAndFollowedId(follower.get().getUserId(),
            followed.get().getUserId());
    final boolean isFollowing = byFollowerIdAndFollowedId.isPresent();
    log.info("Fetched user profile for: [{}]", userId);
    return toProfileResponseDto(followed, isFollowing);
  }

  @Override
  public ProfileResponse followUser(final UserDetailsImpl userDetails, final String username) {
    log.info("Adding user: [{}] as [{}] follower", userDetails.getUsername(), username);
    final Optional<User> followed = userRepository.findByUserName(username);
    validateIfUserExists(username, followed.isEmpty());
    final Optional<User> follower = userRepository.findByUserName(userDetails.getUsername());
    validateIfUserExists(userDetails.getUsername(), follower.isEmpty());
    final Follower followerObject = new Follower();
    followerObject.setFollowerId(follower.get().getUserId());
    followerObject.setFollowedId(followed.get().getUserId());
    followerObject.setCreatedAt(LocalDateTime.now());
    followerRepository.save(followerObject);
    log.info("Added user: [{}] as [{}] follower", userDetails.getUsername(), username);
    return ProfileResponse.builder()
        .username(followed.get().getUserName())
        .bio(followed.get().getBio())
        .image(followed.get().getImage())
        .following(true).build();
  }

  @Override
  public ProfileResponse unfollowUser(final UserDetailsImpl userDetails, final String username) {
    log.info("Removing [{}] from [{}} follower", userDetails.getUsername(), username);
    final Optional<User> followed = userRepository.findByUserName(username);
    validateIfUserExists(username, followed.isEmpty());
    final Optional<User> follower = userRepository.findByUserName(userDetails.getUsername());
    validateIfUserExists(userDetails.getUsername(), follower.isEmpty());
    final Optional<Follower> byFollowerIdAndFollowedId =
        followerRepository.findByFollowerIdAndFollowedId(follower.get().getUserId(), followed.get()
            .getUserId());
    if (byFollowerIdAndFollowedId.isEmpty()) {
      log.error("Unable to find the follower and followed to remove it from db, retry again");
      throw new ApplicationException(Error.FOLLOWER_REMOVAL_EXCEPTION);
    }
    followerRepository.delete(byFollowerIdAndFollowedId.get());
    log.info("Removed follower: [{}], from: [{}]", userDetails.getUsername(), username);
    return ProfileResponse.builder()
        .username(followed.get().getUserName())
        .bio(followed.get().getBio())
        .image(followed.get().getImage())
        .following(false).build();
  }

  private void validateIfUserExists(final String usernameOrId,
                                    final boolean isUserEmpty) {
    if (isUserEmpty) {
      log.error("Unable to find user with username/userId: [{}]", usernameOrId);
      throw new ApplicationException(Error.USERNAME_NOT_FOUND_EXCEPTION);
    }
  }
}
