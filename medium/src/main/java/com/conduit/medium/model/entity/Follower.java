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


/**
 * followers entity class for db.
 */
@Entity
@Table(name = "FOLLOWERS")
@Data
public class Follower {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private final UUID id;
  @Column(name = "follower_id")
  private final UUID followerId;
  @Column(name = "followed_id")
  private final UUID followedId;
  @Column(name = "created_at")
  private final LocalDateTime createdAt;
}
