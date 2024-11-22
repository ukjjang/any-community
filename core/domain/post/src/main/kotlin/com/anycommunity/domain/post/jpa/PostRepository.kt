package com.anycommunity.domain.post.jpa

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.Post
import com.anycommunity.domain.post.toEntity
import com.anycommunity.domain.post.toModel
import com.anycommunity.infra.mysql.post.jpa.PostEntityRepository

@Repository
class PostRepository(
    private val postEntityRepository: PostEntityRepository,
) {
    fun save(post: Post) = postEntityRepository.save(post.toEntity()).toModel()

    fun delete(post: Post) = postEntityRepository.delete(post.toEntity())

    fun findById(id: Long) = postEntityRepository.findByIdOrNull(id)?.toModel()

    fun existsByTitle(title: PostTitle) = postEntityRepository.existsByTitle(title)
}
