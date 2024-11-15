CREATE TABLE `point_game_probability`
(
    `id`          BIGINT   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `point`       BIGINT   NOT NULL COMMENT '포인트',
    `probability` INT      NOT NULL COMMENT '지급 확률 (0 ~ 10000)',
    `created_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updated_at`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='포인트 게임 확률';

INSERT INTO `point_game_probability` (`point`, `probability`)
VALUES (1000, 100), -- 1% 확률로 1000 포인트 지급
       (500, 500),  -- 5% 확률로 500 포인트 지급
       (250, 1000), -- 10% 확률로 250 포인트 지급
       (100, 1800), -- 18% 확률로 100 포인트 지급
       (50, 3300),  -- 33% 확률로 50 포인트 지급
       (0, 3300); -- 33% 확률로 꽝
