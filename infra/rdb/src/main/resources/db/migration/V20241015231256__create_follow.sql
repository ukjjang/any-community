CREATE TABLE `follow`
(
    `id`                BIGINT   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `follower_user_id`  BIGINT   NOT NULL COMMENT '팔로워(팔로우 한) 유저 id',
    `following_user_id` BIGINT   NOT NULL COMMENT '팔로잉(팔로우 된) 유저 id',
    `created_at`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updated_at`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uniq_follower_user_id_following_user_id` (`follower_user_id`, `following_user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='팔로우'
;

