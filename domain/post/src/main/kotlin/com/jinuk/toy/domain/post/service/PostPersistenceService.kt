package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.jpa.PostRepository
import org.springframework.stereotype.Service

@Service
class PostPersistenceService(
    private val postRepository: PostRepository
) {

    fun persist(post: Post): Post {

        return postRepository.save(post)
    }
}
