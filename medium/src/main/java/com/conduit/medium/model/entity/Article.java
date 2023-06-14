package com.conduit.medium.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * articles entity class for db.
 */
@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
public class Article {
  @Id
  @Column(name = "article_id")
  private UUID articleId;
  @Column(name = "slug")
  private String slug;
  @Column(name = "title")
  private String title;
  @Column(name = "description")
  private String description;
  @Column(name = "body")
  private String body;
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
