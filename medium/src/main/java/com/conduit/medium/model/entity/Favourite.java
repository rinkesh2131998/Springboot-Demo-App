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
 * favourite data class to map user with favourite articles for db.
 */
@Entity
@Table(name = "FAVOURITES")
@Data
@NoArgsConstructor
public class Favourite {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "article_id")
  private UUID articleId;
}
