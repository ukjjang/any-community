package com.anycommunity.domain.post.jpa

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import com.anycommunity.definition.post.PostSearchCategory
import com.anycommunity.definition.post.PostSearchSortType
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.Post
import com.anycommunity.infra.mysql.post.entity.PostEntity
import com.anycommunity.infra.mysql.post.jdsl.PostJdslRepository
import com.anycommunity.infra.mysql.post.jpa.PostEntityRepository

@Repository
class PostRepository(
    private val postEntityRepository: PostEntityRepository,
    private val postJdslRepository: PostJdslRepository,
) {
    fun save(post: Post) = postEntityRepository.save(post.toEntity()).toModel()

    fun delete(post: Post) = postEntityRepository.delete(post.toEntity())

    fun findById(id: Long) = postEntityRepository.findByIdOrNull(id)?.toModel()

    fun existsByTitle(title: PostTitle) = postEntityRepository.existsByTitle(title)
    fun findByIdIn(ids: List<Long>) = postEntityRepository.findByIdIn(ids).map { it.toModel() }

    fun search(
        keyword: String?,
        pageable: Pageable,
        postSearchCategory: PostSearchCategory,
        sortType: PostSearchSortType,
    ) = postJdslRepository.search(
        keyword = keyword,
        pageable = pageable,
        postSearchCategory = postSearchCategory,
        sortType = sortType,
    ).map { it.toModel() }
}

private fun PostEntity.toModel() = Post(
    _id = id,
    userId = userId,
    title = PostTitle(title),
    category = category,
    content = content,
    commentCount = commentCount,
    likeCount = likeCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

private fun Post.toEntity() = PostEntity(
    id = _id,
    userId = userId,
    title = title.value,
    category = category,
    content = content,
    commentCount = commentCount,
    likeCount = likeCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
