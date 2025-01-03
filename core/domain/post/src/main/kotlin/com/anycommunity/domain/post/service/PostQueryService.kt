package com.anycommunity.domain.post.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.anycommunity.definition.post.PostSearchSortType
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.jpa.PostJdslRepository
import com.anycommunity.domain.post.jpa.PostRepository

@Service
class PostQueryService(
    private val postRepository: PostRepository,
    private val postJdslRepository: PostJdslRepository,
) {
    fun getById(id: Long) = postRepository.findById(id) ?: throw NoSuchElementException("존재하지 않는 게시글입니다.")

    fun existsByTitle(title: PostTitle) = postRepository.existsByTitle(title)

    fun search(keyword: String?, pageable: Pageable, sortType: PostSearchSortType) = postJdslRepository.search(
        keyword = keyword,
        pageable = pageable,
        sortType = sortType,
    )

    fun findByIdIn(ids: List<Long>) = postRepository.findByIdIn(ids)
}
