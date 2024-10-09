package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.repository.PostRepository
import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import org.springframework.stereotype.Service

@Service
class PostCreateService(
    private val postRepository: PostRepository
) {

    operator fun invoke(command: PostCreateCommand): Post {
        require(!postRepository.existsByTitle(command.title.value)) {"이미 존재하는 게시글 제목입니다."}
        return postRepository.save(Post.create(command))
    }
}
