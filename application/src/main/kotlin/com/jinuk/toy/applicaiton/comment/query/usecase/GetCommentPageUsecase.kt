package com.jinuk.toy.applicaiton.comment.query.usecase

import com.jinuk.toy.applicaiton.comment.query.result.GetCommentPageResult
import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.service.CommentQueryService
import com.jinuk.toy.domain.like.LikeType
import com.jinuk.toy.domain.like.service.LikeQueryService
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.domain.user.value.Username
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetCommentPageUsecase(
    private val userQueryService: UserQueryService,
    private val likeQueryService: LikeQueryService,
    private val commentQueryService: CommentQueryService,
) {
    operator fun invoke(query: GetCommentPageQuery): Page<GetCommentPageResult> {
        val parents =
            commentQueryService.findByPostIdAndParentCommentIdIsNullOrderByIdDesc(query.postId, query.pageable())
        val allComments = commentQueryService.findByPostId(query.postId)

        val commentMap = allComments.associateBy { it.id }
        val commentIds = allComments.map { it.id }

        val likes = likeQueryService.findByTargetTypeAndTargetIdIn(LikeType.COMMENT, commentIds.map { it.toString() })
        val likeCountMap = likes.groupBy { it.targetId.toLong() }.mapValues { it.value.size }

        val userIds = allComments.map { it.userId }
        val usernameMap = userQueryService.findByIdIn(userIds).associate { it.id to it.username }

        val isViewerLikeSet =
            query.viewerId?.let { viewerId ->
                likeQueryService.findByUserIdAndTargetTypeAndTargetIdIn(
                    viewerId,
                    LikeType.COMMENT,
                    commentIds.map { it.toString() },
                ).map { like -> like.targetId.toLong() }.toSet()
            } ?: emptySet()

        val commentParentGroup =
            allComments.groupBy { it.parentCommentId }
                .toMutableMap()
                .apply { this[null] = parents.content }
                .toMap()

        val contents =
            buildCommentParentTree(
                parentId = null,
                commentParentGroup = commentParentGroup,
                commentMap = commentMap,
                isViewerLikeSet = isViewerLikeSet,
                usernameMap = usernameMap,
                likeCountMap = likeCountMap,
            )

        return PageImpl(
            contents,
            parents.pageable,
            parents.totalElements,
        )
    }

    private fun buildCommentParentTree(
        parentId: Long?,
        commentParentGroup: Map<Long?, List<Comment>>,
        commentMap: Map<Long, Comment>,
        isViewerLikeSet: Set<Long>,
        usernameMap: Map<Long, Username>,
        likeCountMap: Map<Long, Int>,
    ): List<GetCommentPageResult> {
        return commentParentGroup[parentId]?.mapNotNull { comment ->
            val username = usernameMap[comment.userId] ?: return@mapNotNull null
            val likeCount = likeCountMap[comment.id] ?: 0

            GetCommentPageResult(
                id = comment.id,
                username = username,
                isViewerLike = isViewerLikeSet.contains(comment.id),
                parentCommentId = comment.parentCommentId,
                likeCount = likeCount,
                content = comment.content,
                children =
                    buildCommentParentTree(
                        parentId = comment.id,
                        commentParentGroup = commentParentGroup,
                        commentMap = commentMap,
                        isViewerLikeSet = isViewerLikeSet,
                        usernameMap = usernameMap,
                        likeCountMap = likeCountMap,
                    ).sortedByDescending { it.id },
            )
        } ?: emptyList()
    }
}

data class GetCommentPageQuery(
    val postId: Long,
    val viewerId: Long? = null,
    val page: Int,
    val size: Int,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}
