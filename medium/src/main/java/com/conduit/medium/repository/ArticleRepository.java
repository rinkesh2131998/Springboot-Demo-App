package com.conduit.medium.repository;

import com.conduit.medium.model.entity.Article;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * repository for article table.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
  Optional<Article> findBySlug(String slug);

  @Query("""
      SELECT a FROM Article a
      WHERE
      (:tag IS NULL
          OR a.articleId IN (
              SELECT tta.articleId FROM TagToArticle tta WHERE tta.tagId = (
                  SELECT tagId FROM Tag WHERE name = :tag
              )
          )
      )
      AND
      (:author IS NULL
          OR a.userId IN (
              SELECT userId FROM User WHERE userName = :author
          )
      )
      AND
      (:favorited IS NULL
          OR a.userId IN (
              SELECT userId FROM User WHERE userName = :favorited
          )
      )
      ORDER BY a.createdAt DESC
      """)
  List<Article> findMostRecentArticles(@Param("tag") String tag, @Param("author") String author,
                                       @Param("favorited") String favorited, Pageable pageable);

  List<Article> findByUserIdOrderByCreatedAt(UUID userId);
}
