package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.jpa.PostRepository
import org.springframework.stereotype.Service

@Service
class PostPersistenceService(
    private val postRepository: PostRepository
) {

    fun persist(post: Post): Post {
        require(!postRepository.existsByTitle(post.title.value)) { "이미 존재하는 게시글 제목입니다." }
        return postRepository.save(post)
    }
}
