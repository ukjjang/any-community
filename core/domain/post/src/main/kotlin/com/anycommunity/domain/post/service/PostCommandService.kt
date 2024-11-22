package com.anycommunity.domain.post.service

import org.springframework.stereotype.Service
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.post.Post
import com.anycommunity.domain.post.PostCreateInfo
import com.anycommunity.domain.post.PostUpdateInfo
import com.anycommunity.domain.post.jpa.PostRepository

@Service
class PostCommandService(
    private val postQueryService: PostQueryService,
    private val postRepository: PostRepository,
) {
    fun save(post: Post) = postRepository.save(post)

    fun create(info: PostCreateInfo): Post {
        require(!postQueryService.existsByTitle(info.title)) { "이미 존재하는 게시글 제목입니다." }
        return save(Post.create(info))
    }

    fun update(info: PostUpdateInfo): Post {
        val post = postQueryService.getById(info.id)
        require(post.title == info.title || !postQueryService.existsByTitle(info.title)) {
            "이미 존재하는 게시글 제목입니다."
        }
        return save(post.update(info))
    }

    fun delete(postId: Long, deleteUserId: Long) {
        val post = postQueryService.getById(postId)
        require(post.userId == deleteUserId) { "작성자만 게시글을 삭제할 수 있습니다." }
        postRepository.delete(post)
    }

    fun updateCommentCount(postId: Long, countOperation: CountOperation) = save(
        postQueryService.getById(postId).updateCommentCount(countOperation),
    )

    fun updateLikeCount(postId: Long, countOperation: CountOperation) = save(
        postQueryService.getById(postId).updateLikeCount(countOperation),
    )
}
