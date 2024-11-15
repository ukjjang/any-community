package com.jinuk.toy.applicaiton.comment.query.usecase

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.jinuk.toy.common.util.custompage.CustomPage
import com.jinuk.toy.common.util.custompage.toCustomPage
import com.jinuk.toy.common.value.like.LikeType
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.service.CommentQueryService
import com.jinuk.toy.domain.like.service.LikeQueryService
import com.jinuk.toy.domain.user.service.UserQueryService

@Service
class GetCommentPageUsecase(
    private val userQueryService: UserQueryService,
    private val likeQueryService: LikeQueryService,
    private val commentQueryService: CommentQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetCommentPageQuery): CustomPage<GetCommentPageResult> {
        val parents =
            commentQueryService.findByPostIdAndParentCommentIdIsNullOrderByIdDesc(query.postId, query.pageable())
        val children = commentQueryService.findByPostIdAndParentCommentIdIsNotNull(query.postId)
        val allComments = parents + children

        val commentMap = allComments.associateBy { it.id }
        val commentIds = allComments.map { it.id }
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

        val content =
            buildCommentParentTree(
                parentId = null,
                commentParentGroup = allComments.groupBy { it.parentCommentId },
                commentMap = commentMap,
                isViewerLikeSet = isViewerLikeSet,
                usernameMap = usernameMap,
            )
        return parents.toCustomPage(content)
    }

    private fun buildCommentParentTree(
        parentId: Long?,
        commentParentGroup: Map<Long?, List<Comment>>,
        commentMap: Map<Long, Comment>,
        isViewerLikeSet: Set<Long>,
        usernameMap: Map<Long, Username>,
    ): List<GetCommentPageResult> {
        return commentParentGroup[parentId]?.mapNotNull { comment ->
            val username = usernameMap[comment.userId] ?: return@mapNotNull null

            GetCommentPageResult(
                id = comment.id,
                username = username,
                isViewerLike = isViewerLikeSet.contains(comment.id),
                parentCommentId = comment.parentCommentId,
                likeCount = comment.likeCount,
                content = comment.content,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt,
                children =
                buildCommentParentTree(
                    parentId = comment.id,
                    commentParentGroup = commentParentGroup,
                    commentMap = commentMap,
                    isViewerLikeSet = isViewerLikeSet,
                    usernameMap = usernameMap,
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

data class GetCommentPageResult(
    val id: Long,
    val username: Username,
    val isViewerLike: Boolean,
    val parentCommentId: Long?,
    val likeCount: Long,
    val content: String,
    val children: List<GetCommentPageResult>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
