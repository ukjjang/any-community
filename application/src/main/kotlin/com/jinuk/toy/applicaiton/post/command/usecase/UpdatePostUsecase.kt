package com.jinuk.toy.applicaiton.post.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.common.value.post.PostCategory
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.domain.post.PostUpdateInfo
import com.jinuk.toy.domain.post.service.PostCommandService

@Service
class UpdatePostUsecase(
    private val postCommandService: PostCommandService,
) {
    @Transactional
    operator fun invoke(command: UpdatePostCommand) {
        postCommandService.update(command.toInfo())
    }
}

data class UpdatePostCommand(
    val userId: Long,
    val id: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
) {
    fun toInfo() = PostUpdateInfo(
        id = id,
        userId = userId,
        title = title,
        category = category,
        content = content,
    )
}
