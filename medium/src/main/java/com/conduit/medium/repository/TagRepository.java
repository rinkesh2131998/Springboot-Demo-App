package com.conduit.medium.repository;

import com.conduit.medium.model.entity.Tag;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository for tag table.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
  Optional<Tag> findByName(String name);
}
