package com.conduit.medium.repository;

import com.conduit.medium.model.entity.Article;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository for article table.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
