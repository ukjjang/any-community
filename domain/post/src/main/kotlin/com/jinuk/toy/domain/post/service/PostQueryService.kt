package com.jinuk.toy.domain.post.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.jinuk.toy.common.value.post.PostSearchSortType
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.domain.post.jpa.PostJdslRepository
import com.jinuk.toy.domain.post.jpa.PostRepository

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
}
