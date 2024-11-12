ALTER TABLE `user`
    ADD COLUMN `total_points` BIGINT NOT NULL DEFAULT 0 COMMENT '총합 포인트' AFTER `gender`;

CREATE TABLE `point_transaction`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '포인트 트랜잭션 ID',
    `user_id`         BIGINT       NOT NULL COMMENT '유저 ID',
    `amount`           BIGINT          NOT NULL COMMENT '포인트 값',
    `description`      VARCHAR(255) DEFAULT NULL COMMENT '설명',
    `created_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updated_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='포인트 트랜잭션';

CREATE TABLE `point_rule`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '포인트 규칙 ID',
    `rule_type`    VARCHAR(50)  NOT NULL COMMENT '규칙 타입',
    `amount` BIGINT          NOT NULL COMMENT '포인트 값',
    `description`  VARCHAR(255) DEFAULT NULL COMMENT '설명',
    `created_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updated_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_rule_type` (`rule_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='포인트 규칙';

INSERT INTO `point_rule` (`rule_type`, `amount`, `description`)
VALUES
    ('ATTENDANCE', 10, '출석체크 시 지급되는 포인트'),
    ('POST_CREATION', 50, '게시글 작성 시 지급되는 포인트'),
    ('COMMENT_CREATION', 10, '댓글 작성 시 지급되는 포인트');

