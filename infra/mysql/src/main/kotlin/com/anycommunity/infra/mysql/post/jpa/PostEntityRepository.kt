package com.anycommunity.infra.mysql.post.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.infra.mysql.post.entity.PostEntity

interface PostEntityRepository : JpaRepository<PostEntity, Long> {
    fun existsByTitle(title: PostTitle): Boolean
}
