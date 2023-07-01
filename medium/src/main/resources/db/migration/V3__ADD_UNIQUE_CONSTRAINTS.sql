ALTER TABLE favourites
ADD CONSTRAINT uk_user_article UNIQUE (user_id, article_id);
