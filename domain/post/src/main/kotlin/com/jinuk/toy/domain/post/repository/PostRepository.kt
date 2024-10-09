package com.jinuk.toy.domain.post.repository

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.infra.rdb.post.jpa.PostEntityRepository
import com.jinuk.toy.domain.post.mapper.PostEntityMapper.toEntity
import com.jinuk.toy.domain.post.mapper.PostEntityMapper.toModel
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PostRepository(
    private val postEntityRepository: PostEntityRepository,
) {
    fun save(post: Post) = postEntityRepository.save(post.toEntity()).toModel()

    fun delete(post: Post) = postEntityRepository.delete(post.toEntity())

    fun findById(id: Long) = postEntityRepository.findByIdOrNull(id)?.toModel()

    fun findByIdIn(ids: List<Long>): List<Post> = postEntityRepository.findAllById(ids).map { it.toModel() }

    fun existsByTitle(title: String) = postEntityRepository.existsByTitle(title)
}

