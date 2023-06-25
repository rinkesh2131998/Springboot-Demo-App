package com.conduit.medium.repository;

import com.conduit.medium.model.entity.Follower;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository of follower table.
 */
@Repository
public interface FollowerRepository extends JpaRepository<Follower, UUID> {

  //  @Query("SELECT * FROM followers WHERE follower_id = :followerId AND followed_id = :followedId")
  Optional<Follower> findByFollowerIdAndFollowedId(UUID followerId, UUID followedId);
}
