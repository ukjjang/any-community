package com.jinuk.toy.domain.post.service

import com.fasterxml.jackson.core.type.TypeReference
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.infra.redis.cache.cached
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class PostQueryService(
    private val postRepository: PostRepository,
) {
    fun getById(id: Long) =
        cached(
            key = cacheKeyByGetById(id),
            expire = Duration.ofMinutes(10),
            returnType = object : TypeReference<Post>() {},
        ) {
            postRepository.findById(id) ?: throw NoSuchElementException("존재하지 않는 게시글입니다.")
        }

    fun existsByTitle(title: String) = postRepository.existsByTitle(title)

    fun existsById(postId: Long) = postRepository.existsById(postId)

    fun findByTitleStartsWithIgnoreCaseOrderByIdDesc(
        title: String,
        pageable: Pageable,
    ) = postRepository.findByTitleStartsWithIgnoreCaseOrderByIdDesc(
        title,
        pageable,
    )

    fun findByOrderByIdDesc(pageable: Pageable) = postRepository.findByOrderByIdDesc(pageable)
}

fun cacheKeyByGetById(id: Long) = "PostQueryService.getById.id:$id"
