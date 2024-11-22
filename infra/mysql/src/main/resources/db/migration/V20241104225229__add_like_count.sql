ALTER TABLE `post`
    ADD COLUMN `like_count` BIGINT NOT NULL DEFAULT 0 AFTER `comment_count`;

ALTER TABLE `comment`
    ADD COLUMN `like_count` BIGINT NOT NULL DEFAULT 0 AFTER `content`;
