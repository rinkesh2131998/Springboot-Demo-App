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
 * tagList table class for db.
 */
@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "tag_id")
  private UUID tagId;
  @Column(name = "name")
  private String name;
}
