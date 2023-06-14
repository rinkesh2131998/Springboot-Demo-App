package com.conduit.medium.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * entity class for users table.
 */
@Entity
@Table(name = "users")
@Getter
public class User {
  @Column(name = "user_name")
  private final String userName;
  @Column(name = "email")
  private final String email;
  @Column(name = "password")
  private final String password;
  @Column(name = "bio")
  private final String bio;
  @Column(name = "image")
  private final String image;
  @Column(name = "created_at")
  private final LocalDateTime createdAt;
  @Column(name = "updated_at")
  private final LocalDateTime updatedAt;
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Setter
  private UUID userId;

  public User(final String userName, final String email, final String password, final String bio,
              final String image, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.bio = bio;
    this.image = image;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
