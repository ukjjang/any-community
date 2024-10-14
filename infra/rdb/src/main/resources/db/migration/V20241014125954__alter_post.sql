ALTER TABLE `post`
    ADD COLUMN content TEXT NOT NULL AFTER title;

ALTER TABLE `post`
    ADD COLUMN user_id BIGINT NOT NULL AFTER id;

CREATE INDEX idx_user_id ON `post` (user_id);
