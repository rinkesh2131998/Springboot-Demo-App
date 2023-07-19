package com.conduit.medium.repository;

import com.conduit.medium.model.entity.Article;
import com.conduit.medium.util.Constant;
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

  @Query(Constant.RECENT_ARTICLES_QUERY)
  List<Article> findMostRecentArticles(@Param("tag") String tag, @Param("author") String author,
                                       @Param("favorited") String favorited, Pageable pageable);

  List<Article> findByUserIdOrderByCreatedAt(UUID userId);
}
