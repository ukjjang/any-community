package com.jinuk.toy.infra.rdb.comment.jpa

import com.jinuk.toy.infra.rdb.comment.entity.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentEntityRepository : JpaRepository<CommentEntity, Long>
