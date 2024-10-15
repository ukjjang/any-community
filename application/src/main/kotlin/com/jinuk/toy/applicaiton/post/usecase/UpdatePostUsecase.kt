package com.jinuk.toy.applicaiton.post.usecase

import com.jinuk.toy.applicaiton.post.command.UpdatePostCommand
import com.jinuk.toy.applicaiton.post.command.update
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostPersistenceService
import com.jinuk.toy.domain.post.service.PostQueryService
import org.springframework.stereotype.Service

@Service
class UpdatePostUsecase(
    private val postQueryService: PostQueryService,
    private val postPersistenceService: PostPersistenceService,
) {

    operator fun invoke(command: UpdatePostCommand): Post {
        require(!postQueryService.existsByTitle(command.title.value)) { "이미 존재하는 게시글 제목입니다." }
        val post = postQueryService.getById(command.id)
        val updatedPost = post.update(command)
        return postPersistenceService.persist(updatedPost)
    }
}
