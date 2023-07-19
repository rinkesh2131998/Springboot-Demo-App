package com.conduit.medium.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "comments_seq_generator")
  @SequenceGenerator(name = "comments_seq_generator", sequenceName = "comments_seq", allocationSize = 1)
  @Column(name = "comment_id")
  private long commentId;
  @Column(name = "body")
  private String body;
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "article_id")
  private UUID articleId;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
