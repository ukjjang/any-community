package com.jinuk.toy.domain.post.jpa

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
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

    fun existsByTitle(title: String) = postEntityRepository.existsByTitle(title)

    fun findByTitleStartsWithIgnoreCase(
        title: String,
        pageable: Pageable,
    ) = postEntityRepository.findByTitleStartsWithIgnoreCase(title, pageable).map { it.toModel() }

    fun findBy(pageable: Pageable) =
        postEntityRepository.findBy(
            pageable,
        ).map { it.toModel() }
}
