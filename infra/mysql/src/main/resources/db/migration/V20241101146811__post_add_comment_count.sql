ALTER TABLE `post`
    ADD COLUMN `comment_count` BIGINT NOT NULL DEFAULT 0 AFTER `content`;
