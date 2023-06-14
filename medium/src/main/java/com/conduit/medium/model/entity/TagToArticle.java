package com.conduit.medium.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * relationship table between tag and articles for db.
 */
@Entity
@Table(name = "tags_to_articles")
@Data
@NoArgsConstructor
public class TagToArticle {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;
  @Column(name = "article_id")
  private UUID articleId;
  @Column(name = "tag_id")
  private UUID tagId;
}
