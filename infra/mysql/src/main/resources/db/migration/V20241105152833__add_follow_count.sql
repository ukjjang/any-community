ALTER TABLE `user`
    ADD COLUMN `following_count` BIGINT NOT NULL DEFAULT 0 COMMENT '팔로잉 수' AFTER `password`;
ALTER TABLE `user`
    ADD COLUMN `follower_count` BIGINT NOT NULL DEFAULT 0 COMMENT '팔로워 수' AFTER `following_count`;
