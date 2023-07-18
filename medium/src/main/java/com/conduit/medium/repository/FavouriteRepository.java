package com.conduit.medium.repository;

import com.conduit.medium.model.entity.Favourite;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository for favourite table.
 */
@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, UUID> {
  boolean existsByUserIdAndArticleId(UUID userId, UUID articleId);

  long countByArticleId(UUID articleId);

  Optional<Favourite> findByUserIdAndArticleId(UUID userId, UUID articleId);
}
