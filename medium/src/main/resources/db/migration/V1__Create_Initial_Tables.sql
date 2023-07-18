CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--Create 'users' table
CREATE TABLE IF NOT EXISTS users (
  user_id UUID PRIMARY KEY,
  user_name VARCHAR NOT NULL,
  email VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  bio TEXT,
  image VARCHAR,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP
);

--Create "followers" table
CREATE TABLE IF NOT EXISTS followers (
    id UUID PRIMARY KEY,
    follower_id UUID NOT NULL,
    followed_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_follower FOREIGN KEY (follower_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_followed FOREIGN KEY (followed_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (follower_id, followed_id)
);

--Create "articles" table
CREATE TABLE IF NOT EXISTS articles (
    article_id UUID PRIMARY KEY,
    slug VARCHAR UNIQUE NOT NULL,
    title VARCHAR NOT NULL,
    description TEXT,
    body TEXT,
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

--Create "tags" table
CREATE TABLE IF NOT EXISTS tags (
    tag_id UUID PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

--Create "tags_to_articles" table to maintain relation btw tags and articles
CREATE TABLE IF NOT EXISTS tags_to_articles (
    id UUID PRIMARY KEY,
    article_id UUID NOT NULL,
    tag_id UUID NOT NULL,
    CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE,
    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE,
    UNIQUE (article_id, tag_id)
);

--Create "comments" table
CREATE TABLE IF NOT EXISTS comments (
    comment_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    body TEXT,
    user_id UUID NOT NULL,
    article_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE
);

--Create "favourites" table
CREATE TABLE IF NOT EXISTS favourites (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    article_id UUID NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES articles(article_id) ON DELETE CASCADE
);