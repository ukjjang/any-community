CREATE TABLE outbox
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `topic`      VARCHAR(255) NOT NULL COMMENT 'topic',
    `payload`    JSON         NOT NULL COMMENT '이벤트 데이터',
    `status`     VARCHAR(255) NOT NULL COMMENT '상태',
    `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`),
    KEY `idx_topic` (`topic`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='outbox';
