-- update all tables to use auto generated uuid's as PK

-- Update 'users' table
ALTER TABLE users
ALTER COLUMN user_id SET DEFAULT gen_random_uuid();

-- Update "followers" table
ALTER TABLE followers
ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Update "articles" table
ALTER TABLE articles
ALTER COLUMN article_id SET DEFAULT gen_random_uuid();

-- Update "tags" table
ALTER TABLE tags
ALTER COLUMN tag_id SET DEFAULT gen_random_uuid();

-- Update "tags_to_articles" table
ALTER TABLE tags_to_articles
ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Update "favourites" table
ALTER TABLE favourites
ALTER COLUMN id SET DEFAULT gen_random_uuid();
