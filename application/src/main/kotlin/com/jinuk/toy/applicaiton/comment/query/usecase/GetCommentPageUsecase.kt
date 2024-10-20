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
            query.viewerId?.let {
                likeQueryService.findByUserIdAndTargetTypeAndTargetIdIn(
                    it,
                    LikeType.COMMENT,
                    commentIds.map { comment -> comment.toString() },
                ).map { like -> like.targetId.toLong() }.toSet()
            } ?: emptySet()

        val commentParentGroup = allComments.groupBy { it.parentCommentId }.toMutableMap()
        return buildCommentParentTree(
            parentId = null,
            parents = parents,
            commentParentGroup = commentParentGroup,
            commentMap = commentMap,
            isViewerLikeSet = isViewerLikeSet,
            usernameMap = usernameMap,
            likeCountMap = likeCountMap,
        )
    }

    private fun buildCommentParentTree(
        parentId: Long?,
        parents: Page<Comment>,
        commentParentGroup: Map<Long?, List<Comment>>,
        commentMap: Map<Long, Comment>,
        isViewerLikeSet: Set<Long>,
        usernameMap: Map<Long, Username>,
        likeCountMap: Map<Long, Int>,
    ): Page<GetCommentPageResult> {
        val comments = parentId?.let { commentParentGroup[it] ?: emptyList() } ?: parents
        val results =
            comments.mapNotNull { comment ->
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
                            parents = parents,
                            commentParentGroup = commentParentGroup,
                            commentMap = commentMap,
                            isViewerLikeSet = isViewerLikeSet,
                            usernameMap = usernameMap,
                            likeCountMap = likeCountMap,
                        ).content.sortedByDescending { it.id },
                )
            }

        return PageImpl(
            results,
            parents.pageable,
            parents.totalElements,
        )
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
