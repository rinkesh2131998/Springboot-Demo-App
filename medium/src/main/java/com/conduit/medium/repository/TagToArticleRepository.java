package com.conduit.medium.repository;

import com.conduit.medium.model.entity.TagToArticle;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository for tagToArticle table.
 */
@Repository
public interface TagToArticleRepository extends JpaRepository<TagToArticle, UUID> {
  List<TagToArticle> findAllByArticleId(UUID articleId);


}
