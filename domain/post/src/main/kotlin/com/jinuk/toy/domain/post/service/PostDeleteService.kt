package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.post.jpa.PostRepository
import org.springframework.stereotype.Service

@Service
class PostDeleteService(
    private val postQueryService: PostQueryService,
    private val postRepository: PostRepository,
) {
    fun delete(postId: Long, deleteUserId: Long) =
        postQueryService.getById(postId)
            .let {
                require(it.userId == deleteUserId) { "작성자만 게시글을 삭제할 수 있습니다." }
                postRepository.delete(it)
            }
}
