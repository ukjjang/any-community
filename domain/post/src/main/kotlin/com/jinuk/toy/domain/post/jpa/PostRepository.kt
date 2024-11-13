package com.jinuk.toy.domain.post.jpa

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.toEntity
import com.jinuk.toy.domain.post.toModel
import com.jinuk.toy.infra.rdb.post.jpa.PostEntityRepository

@Repository
class PostRepository(
    private val postEntityRepository: PostEntityRepository,
) {
    fun save(post: Post) = postEntityRepository.save(post.toEntity()).toModel()

    fun delete(post: Post) = postEntityRepository.delete(post.toEntity())

    fun findById(id: Long) = postEntityRepository.findByIdOrNull(id)?.toModel()

    fun existsByTitle(title: PostTitle) = postEntityRepository.existsByTitle(title)
}
