package com.jinuk.toy.applicaiton.post.usecase

import com.jinuk.toy.applicaiton.post.command.CreatePostCommand
import com.jinuk.toy.applicaiton.post.command.toPost
import com.jinuk.toy.domain.post.service.PostPersistenceService
import org.springframework.stereotype.Service

@Service
class CreatePostUsecase(
    private val postPersistenceService: PostPersistenceService,
) {

    operator fun invoke(command: CreatePostCommand) = command.toPost().let {
        postPersistenceService.persist(it)
    }
}
