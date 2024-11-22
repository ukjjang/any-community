CREATE TABLE comment
(
    id                BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    user_id           BIGINT   NOT NULL COMMENT '유저 id',
    post_id           BIGINT   NOT NULL COMMENT '게시글 id',
    parent_comment_id BIGINT COMMENT '부모 댓글 id',
    content           TEXT     NOT NULL COMMENT '내용',
    created_at        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    updated_at        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    INDEX idx_post_id (post_id),
    INDEX idx_user_id (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='댓글'
;
