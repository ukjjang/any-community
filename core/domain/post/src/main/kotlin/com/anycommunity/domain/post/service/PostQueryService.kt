package com.anycommunity.domain.post.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.anycommunity.definition.post.PostSearchCategory
import com.anycommunity.definition.post.PostSearchSortType
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.jpa.PostRepository

@Service
class PostQueryService(
    private val postRepository: PostRepository,
) {
    fun getById(id: Long) = postRepository.findById(id) ?: throw NoSuchElementException("존재하지 않는 게시글입니다.")

    fun existsByTitle(title: PostTitle) = postRepository.existsByTitle(title)

    fun search(
        keyword: String?,
        pageable: Pageable,
        postSearchCategory: PostSearchCategory,
        sortType: PostSearchSortType,
    ) = postRepository.search(
        keyword = keyword,
        pageable = pageable,
        postSearchCategory = postSearchCategory,
        sortType = sortType,
    )

    fun findByIdIn(ids: List<Long>) = postRepository.findByIdIn(ids)
}
