CREATE TABLE `user`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `username`   VARCHAR(255) NOT NULL COMMENT '사용자 이름',
    `password`   VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updated_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `idx_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='유저'
;
