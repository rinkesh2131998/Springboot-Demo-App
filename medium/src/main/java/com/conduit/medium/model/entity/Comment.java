package com.conduit.medium.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment entity class for db.
 */
@Entity
@Table(name = "COMMENTS")
@Data
@NoArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "comment_id")
  private UUID commentId;
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "article_id")
  private UUID articleId;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
