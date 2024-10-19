package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.infra.redis.cache.cacheEvict
import org.springframework.stereotype.Service

@Service
class PostCommandService(
    private val postQueryService: PostQueryService,
    private val postRepository: PostRepository,
) {
    fun save(post: Post) = postRepository.save(post)

    fun delete(
        postId: Long,
        deleteUserId: Long,
    ) = postQueryService.getById(postId).apply {
        require(userId == deleteUserId) { "작성자만 게시글을 삭제할 수 있습니다." }
    }.let(postRepository::delete).also {
        cacheEvict(cacheKeyByGetById(postId))
    }
}
