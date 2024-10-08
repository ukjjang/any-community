package com.jinuk.toy.infra.rdb.post.jpa

import com.jinuk.toy.infra.rdb.post.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostEntityRepository : JpaRepository<PostEntity, Long>
