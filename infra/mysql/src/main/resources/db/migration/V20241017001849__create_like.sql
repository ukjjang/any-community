CREATE TABLE `like`
(
    id          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    user_id     BIGINT       NOT NULL COMMENT '유저 id',
    target_type VARCHAR(255) NOT NULL COMMENT '타겟 type',
    target_id   VARCHAR(255) NOT NULL COMMENT '타겟 id',
    created_at  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    updated_at  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    INDEX idx_user_id (user_id),
    INDEX idx_target_type (target_type),
    INDEX idx_target_id (target_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='좋아요'
;
