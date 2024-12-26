CREATE TABLE user_feed
(
    `id`             BIGINT   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`        BIGINT   NOT NULL COMMENT '사용자 ID',
    `post_id`        BIGINT   NOT NULL COMMENT '게시글 ID',
    `post_author_id` BIGINT   NOT NULL COMMENT '게시글 작성자 ID',
    `created_at`     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updated_at`     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_user_id_post_id` (`user_id`, `post_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_post_id` (`post_id`),
    KEY `idx_post_author_id` (`post_author_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='user_feed';
