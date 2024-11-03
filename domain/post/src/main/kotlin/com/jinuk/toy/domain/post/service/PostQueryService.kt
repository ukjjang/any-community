package com.jinuk.toy.domain.post.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.jinuk.toy.domain.post.jpa.PostRepository

@Service
class PostQueryService(
    private val postRepository: PostRepository,
) {
    fun getById(id: Long) = postRepository.findById(id) ?: throw NoSuchElementException("존재하지 않는 게시글입니다.")

    fun existsByTitle(title: String) = postRepository.existsByTitle(title)

    fun findByTitleStartsWithIgnoreCaseOrderByIdDesc(
        title: String,
        pageable: Pageable,
    ) = postRepository.findByTitleStartsWithIgnoreCaseOrderByIdDesc(
        title,
        pageable,
    )

    fun findByOrderByIdDesc(pageable: Pageable) = postRepository.findByOrderByIdDesc(pageable)
}
