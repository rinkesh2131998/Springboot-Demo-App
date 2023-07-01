package com.conduit.medium.repository;

import com.conduit.medium.model.entity.Comment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository for comments table.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
