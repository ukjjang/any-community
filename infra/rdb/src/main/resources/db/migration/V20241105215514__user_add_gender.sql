ALTER TABLE `user`
    ADD COLUMN `gender` VARCHAR(40) NOT NULL DEFAULT 0 COMMENT '팔로잉 수' AFTER `password`;