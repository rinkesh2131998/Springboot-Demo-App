package com.conduit.medium.repository;

import com.conduit.medium.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository of user table.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUserName(String userName);

  Optional<User> findByEmail(String email);

  boolean existsByUserName(String userName);

  boolean existsByEmail(String email);
}
