package com.jinuk.toy.infra.rdb.post.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.jinuk.toy.infra.rdb.post.entity.PostEntity

interface PostEntityRepository : JpaRepository<PostEntity, Long> {
    fun existsByTitle(title: String): Boolean
}
