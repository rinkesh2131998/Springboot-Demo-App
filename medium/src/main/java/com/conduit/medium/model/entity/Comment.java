package com.conduit.medium.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * comment entity class for db.
 */
@Entity
@Table(name = "COMMENTS")
@Data
public class Comment {
  @Id
  @Column(name = "comment_id")
  private final UUID commentId;
  @Column(name = "user_id")
  private final UUID userId;
  @Column(name = "article_id")
  private final UUID articleId;
  @Column(name = "created_at")
  private final LocalDateTime createdAt;
  @Column(name = "updated_at")
  private final LocalDateTime updatedAt;
}
