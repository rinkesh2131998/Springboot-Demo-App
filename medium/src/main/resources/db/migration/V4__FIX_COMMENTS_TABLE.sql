-- Create a new sequence for the auto-incrementing comment_id
CREATE SEQUENCE comments_seq;

-- Drop the primary key constraint and comment_id column
-- (assuming "comments" is the table name)
ALTER TABLE comments DROP CONSTRAINT comments_pkey;
ALTER TABLE comments DROP COLUMN comment_id;

-- Add the comment_id column with auto-increment behavior using the new sequence
ALTER TABLE comments ADD COLUMN comment_id BIGINT PRIMARY KEY DEFAULT nextval('comments_seq');
