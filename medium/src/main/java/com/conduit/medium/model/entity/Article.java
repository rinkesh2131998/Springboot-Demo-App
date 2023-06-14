package com.conduit.medium.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * articles entity class for db.
 */
@Entity
@Table(name = "articles")
@Data
public class Article {
  @Id
  @Column(name = "article_id")
  private final UUID articleId;
  @Column(name = "slug")
  private final String slug;
  @Column(name = "title")
  private final String title;
  @Column(name = "description")
  private final String description;
  @Column(name = "body")
  private final String body;
  @Column(name = "user_id")
  private final UUID userId;
  @Column(name = "created_at")
  private final LocalDateTime createdAt;
  @Column(name = "updated_at")
  private final LocalDateTime updatedAt;
}
