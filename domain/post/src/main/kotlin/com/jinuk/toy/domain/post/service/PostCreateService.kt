package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.repository.PostRepository
import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import org.springframework.stereotype.Service

@Service
class PostCreateService(
    private val postRepository: PostRepository
) {

    operator fun invoke(command: PostCreateCommand) = postRepository.save(Post.create(command))
}
